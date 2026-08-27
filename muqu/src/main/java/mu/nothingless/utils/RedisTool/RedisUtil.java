package mu.nothingless.utils.RedisTool;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redis 工具类
 *
 * <p>
 * 封装 Spring Data Redis 与 Redisson 高级特性：
 * </p>
 * <ul>
 * <li>基础数据类型（String / Hash / List / Set / ZSet）</li>
 * <li>分布式锁（Redisson，支持看门狗自动续期）</li>
 * <li>限流器（Redisson RateLimiter）</li>
 * <li>布隆过滤器（Redisson BloomFilter）</li>
 * <li>缓存防击穿 / 防穿透模板方法</li>
 * <li>对象 JSON 存取（基于 Fastjson2）</li>
 * </ul>
 *
 * <p>
 * 所有操作均包含异常捕获与日志，Redis 异常不会向上传播导致业务中断
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    // ==================== Key 操作 ====================

    /**
     * 删除 Key
     */
    public Boolean delete(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis delete failed, key={}", key, e);
            return false;
        }
    }

    /**
     * 批量删除
     */
    public Long delete(Collection<String> keys) {
        try {
            return stringRedisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("Redis batch delete failed", e);
            return 0L;
        }
    }

    /**
     * 判断 Key 是否存在
     */
    public Boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis hasKey failed, key={}", key, e);
            return false;
        }
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, Duration timeout) {
        try {
            return stringRedisTemplate.expire(key, timeout);
        } catch (Exception e) {
            log.error("Redis expire failed, key={}", key, e);
            return false;
        }
    }

    /**
     * 获取过期时间（秒），-2 表示 Key 不存在，-1 表示永不过期
     */
    public Long getExpire(String key) {
        try {
            return stringRedisTemplate.getExpire(key);
        } catch (Exception e) {
            log.error("Redis getExpire failed, key={}", key, e);
            return -2L;
        }
    }

    // ==================== String 操作 ====================

    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis get failed, key={}", key, e);
            return null;
        }
    }

    public void set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Redis set failed, key={}", key, e);
        }
    }

    public void setEx(String key, String value, Duration timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout);
        } catch (Exception e) {
            log.error("Redis setEx failed, key={}", key, e);
        }
    }

    /**
     * SETNX（Key 不存在时才设置），常用于简单互斥场景
     */
    public Boolean setNx(String key, String value, Duration timeout) {
        try {
            return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout);
        } catch (Exception e) {
            log.error("Redis setNx failed, key={}", key, e);
            return false;
        }
    }

    public Long incr(String key) {
        try {
            return stringRedisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            log.error("Redis incr failed, key={}", key, e);
            return null;
        }
    }

    public Long decr(String key) {
        try {
            return stringRedisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            log.error("Redis decr failed, key={}", key, e);
            return null;
        }
    }

    // ==================== Object 操作（Fastjson2 序列化）====================

    /**
     * 存储对象（JSON 序列化）
     */
    public void setObject(String key, Object value, Duration timeout) {
        try {
            String json = JSON.toJSONString(value);
            stringRedisTemplate.opsForValue().set(key, json, timeout);
        } catch (Exception e) {
            log.error("Redis setObject failed, key={}", key, e);
        }
    }

    /**
     * 获取对象（JSON 反序列化）
     */
    public <T> T getObject(String key, Class<T> clazz) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json == null || isNullValue(json))
                return null;
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("Redis getObject failed, key={}", key, e);
            return null;
        }
    }

    // ==================== Hash 操作 ====================

    public String hGet(String key, String field) {
        try {
            return (String) stringRedisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("Redis hGet failed, key={}, field={}", key, field, e);
            return null;
        }
    }

    public void hSet(String key, String field, String value) {
        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            log.error("Redis hSet failed, key={}, field={}", key, field, e);
        }
    }

    public void hPutAll(String key, Map<String, String> map) {
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            log.error("Redis hPutAll failed, key={}", key, e);
        }
    }

    public Map<Object, Object> hGetAll(String key) {
        try {
            return stringRedisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("Redis hGetAll failed, key={}", key, e);
            return Collections.emptyMap();
        }
    }

    public Long hDel(String key, Object... fields) {
        try {
            return stringRedisTemplate.opsForHash().delete(key, fields);
        } catch (Exception e) {
            log.error("Redis hDel failed, key={}", key, e);
            return 0L;
        }
    }

    public Boolean hHasKey(String key, String field) {
        try {
            return stringRedisTemplate.opsForHash().hasKey(key, field);
        } catch (Exception e) {
            log.error("Redis hHasKey failed, key={}, field={}", key, field, e);
            return false;
        }
    }

    // ==================== List 操作 ====================

    public Long lPush(String key, String... values) {
        try {
            return stringRedisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            log.error("Redis lPush failed, key={}", key, e);
            return 0L;
        }
    }

    public Long rPush(String key, String... values) {
        try {
            return stringRedisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("Redis rPush failed, key={}", key, e);
            return 0L;
        }
    }

    public String lPop(String key) {
        try {
            return stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("Redis lPop failed, key={}", key, e);
            return null;
        }
    }

    public String bLPop(String key, Duration timeout) {
        try {
            return stringRedisTemplate.opsForList().leftPop(key, timeout);
        } catch (Exception e) {
            log.error("Redis bLPop failed, key={}", key, e);
            return null;
        }
    }

    public List<String> lRange(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis lRange failed, key={}", key, e);
            return Collections.emptyList();
        }
    }

    public Long lLen(String key) {
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("Redis lLen failed, key={}", key, e);
            return 0L;
        }
    }

    // ==================== Set 操作 ====================

    public Long sAdd(String key, String... members) {
        try {
            return stringRedisTemplate.opsForSet().add(key, members);
        } catch (Exception e) {
            log.error("Redis sAdd failed, key={}", key, e);
            return 0L;
        }
    }

    public Set<String> sMembers(String key) {
        try {
            return stringRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis sMembers failed, key={}", key, e);
            return Collections.emptySet();
        }
    }

    public Boolean sIsMember(String key, String member) {
        try {
            return stringRedisTemplate.opsForSet().isMember(key, member);
        } catch (Exception e) {
            log.error("Redis sIsMember failed, key={}", key, e);
            return false;
        }
    }

    public Long sRem(String key, Object... members) {
        try {
            return stringRedisTemplate.opsForSet().remove(key, members);
        } catch (Exception e) {
            log.error("Redis sRem failed, key={}", key, e);
            return 0L;
        }
    }

    public Long sCard(String key) {
        try {
            return stringRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("Redis sCard failed, key={}", key, e);
            return 0L;
        }
    }

    // ==================== ZSet 操作 ====================

    public Boolean zAdd(String key, String member, double score) {
        try {
            return stringRedisTemplate.opsForZSet().add(key, member, score);
        } catch (Exception e) {
            log.error("Redis zAdd failed, key={}", key, e);
            return false;
        }
    }

    public Set<String> zRange(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis zRange failed, key={}", key, e);
            return Collections.emptySet();
        }
    }

    public Long zRem(String key, Object... members) {
        try {
            return stringRedisTemplate.opsForZSet().remove(key, members);
        } catch (Exception e) {
            log.error("Redis zRem failed, key={}", key, e);
            return 0L;
        }
    }

    public Double zScore(String key, String member) {
        try {
            return stringRedisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            log.error("Redis zScore failed, key={}", key, e);
            return null;
        }
    }

    public Long zRank(String key, String member) {
        try {
            return stringRedisTemplate.opsForZSet().rank(key, member);
        } catch (Exception e) {
            log.error("Redis zRank failed, key={}", key, e);
            return null;
        }
    }

    // ==================== 分布式锁（Redisson）====================

    /**
     * 获取可重入锁（支持看门狗自动续期，默认锁超时 30s）
     */
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 尝试获取锁（非阻塞，指定租约时间，不会自动续期）
     *
     * @param lockKey   锁名称
     * @param waitTime  最大等待时间
     * @param leaseTime 租约时间（到期自动释放）
     */
    public boolean tryLock(String lockKey, Duration waitTime, Duration leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime.toMillis(), leaseTime.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("获取锁被中断, lockKey={}", lockKey);
            return false;
        } catch (Exception e) {
            log.error("获取锁异常, lockKey={}", lockKey, e);
            return false;
        }
    }

    /**
     * 尝试获取锁（看门狗自动续期模式，leaseTime 传 -1）
     */
    public boolean tryLock(String lockKey, Duration waitTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime.toMillis(), -1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("获取锁被中断, lockKey={}", lockKey);
            return false;
        } catch (Exception e) {
            log.error("获取锁异常, lockKey={}", lockKey, e);
            return false;
        }
    }

    /**
     * 释放锁（安全释放：仅释放当前线程持有的锁）
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.error("释放锁异常, lockKey={}", lockKey, e);
        }
    }

    /**
     * 模板方法：安全执行带锁业务（自动释放锁，使用看门狗续期）
     *
     * @param lockKey  锁名称
     * @param waitTime 最大等待时间
     * @param supplier 业务逻辑
     * @param <T>      返回值类型
     * @return 业务结果，获取锁失败返回 null
     */
    public <T> T executeWithLock(String lockKey, Duration waitTime, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime.toMillis(), -1, TimeUnit.MILLISECONDS);
            if (!locked) {
                log.warn("获取锁失败, lockKey={}", lockKey);
                return null;
            }
            return supplier.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("获取锁被中断, lockKey={}", lockKey);
            return null;
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    log.error("释放锁异常, lockKey={}", lockKey, e);
                }
            }
        }
    }

    // ==================== 限流器（Redisson RateLimiter）====================

    /**
     * 获取限流器（如果已存在则更新速率）
     *
     * @param rateKey 限流器名称
     * @param rate    速率（如 10 表示每秒产生 10 个令牌）
     * @param burst   突发容量（桶大小）
     */
    public RRateLimiter getRateLimiter(String rateKey, long rate, long burst) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateKey);
        rateLimiter.trySetRate(
                RateType.OVERALL,
                rate,
                Duration.ofSeconds(1));
        return rateLimiter;
    }

    /**
     * 尝试获取一个令牌
     *
     * @return true 表示允许通过，false 表示被限流
     */
    public boolean tryAcquire(String rateKey, long rate, long burst) {
        try {
            return getRateLimiter(rateKey, rate, burst).tryAcquire();
        } catch (Exception e) {
            log.error("限流器异常, rateKey={}", rateKey, e);
            // 限流器故障时默认放行，避免雪崩；也可根据业务改为 false
            return true;
        }
    }

    // ==================== 布隆过滤器（Redisson BloomFilter）====================

    /**
     * 初始化布隆过滤器
     *
     * @param bloomKey  过滤器名称
     * @param capacity  预期元素数量
     * @param errorRate 误判率（如 0.01 表示 1%）
     */
    public <T> RBloomFilter<T> createBloomFilter(String bloomKey, long capacity, double errorRate) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(bloomKey);
        bloomFilter.tryInit(capacity, errorRate);
        return bloomFilter;
    }

    public <T> boolean addToBloomFilter(String bloomKey, T element) {
        try {
            RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(bloomKey);
            return bloomFilter.add(element);
        } catch (Exception e) {
            log.error("布隆过滤器添加异常, bloomKey={}", bloomKey, e);
            return false;
        }
    }

    public <T> boolean containsInBloomFilter(String bloomKey, T element) {
        try {
            RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(bloomKey);
            return bloomFilter.contains(element);
        } catch (Exception e) {
            log.error("布隆过滤器查询异常, bloomKey={}", bloomKey, e);
            // 异常时默认认为可能存在（降级走正常查询）
            return true;
        }
    }

    // ==================== 缓存防击穿 / 防穿透 ====================

    /**
     * 缓存空值标记（解决缓存穿透）
     */
    public void setNullValue(String key, Duration timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, "NULL", timeout);
        } catch (Exception e) {
            log.error("Redis setNullValue failed, key={}", key, e);
        }
    }

    /**
     * 判断是否为缓存空值标记
     */
    public boolean isNullValue(String value) {
        return "NULL".equals(value);
    }

    /**
     * 缓存击穿保护模板（热点 Key 失效时，仅一个线程查库，其余等待后重读缓存）
     *
     * @param key       缓存 Key
     * @param lockWait  抢锁最大等待时间
     * @param lockLease 锁持有时间（查库 + 写缓存应在该时间内完成）
     * @param dbLoader  数据库查询逻辑
     * @return 查询结果
     */
    public String loadWithLock(String key, Duration lockWait, Duration lockLease, Supplier<String> dbLoader) {
        String value = get(key);
        if (value != null) {
            return isNullValue(value) ? null : value;
        }

        boolean locked = tryLock("lock:" + key, lockWait, lockLease);
        if (!locked) {
            // 没抢到锁，等 200ms 再读一次缓存
            sleepQuietly(200);
            value = get(key);
            return value != null && !isNullValue(value) ? value : null;
        }

        try {
            // 双重检查
            value = get(key);
            if (value != null) {
                return isNullValue(value) ? null : value;
            }

            value = dbLoader.get();
            if (value != null) {
                set(key, value);
            } else {
                setNullValue(key, Duration.ofMinutes(5));
            }
            return value;
        } finally {
            unlock("lock:" + key);
        }
    }

    private void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
