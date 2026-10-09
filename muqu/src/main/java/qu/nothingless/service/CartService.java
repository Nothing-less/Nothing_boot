package qu.nothingless.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import qu.nothingless.entity.CartItem;
import qu.nothingless.exceptions.CartException;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * 购物车服务 —— 数据全部落在 Redis，JVM 内不保留任何状态。
 *
 * <h3>存储结构</h3>
 *
 * <pre>
 *   cart:{userId}             Hash   field = skuId(String)   value = CartItem JSON
 *   cart:guest:{sha256(token)} Hash  同上，访客态临时购物车
 * </pre>
 *
 * 选 Hash 而非把整个购物车序列化成一个 String，是因为
 * 增删单个商品是 O(1)，不必反序列化整包，网络开销也更小。
 *
 * <h3>并发模型</h3>
 * 同一个购物车的写操作都使用 Redisson 分布式锁，避免覆盖写和累加写互相覆盖。
 * 锁使用固定租约（不使用 watchdog）：购物车操作都是毫秒级，线程卡死时
 * 固定租约可以让锁在 LEASE 秒后自动释放，避免"活着但永远不干活"的线程把锁占死。
 *
 * <h3>写入模型</h3>
 * 所有写入走 WRITE_SCRIPT（单 key Lua），把「种类数上限校验 + HSET + EXPIRE」
 * 合并成一次原子调用。原因：拆成多条命令时，HSET 创建出的 key 在 EXPIRE 之前
 * 进程崩溃，会留下一个没有 TTL 的永久 key。
 */
@Slf4j
@Service
public class CartService {

    /**
     * 显式创建序列化器，不依赖容器里的 ObjectMapper bean：
     * Boot 4 默认走 Jackson 3（tools.jackson.*），本项目又引了 spring-boot-jackson2 桥接，
     * 两条线同时存在时按类型注入容易歧义，这里自己 new 一个最稳。
     */
    private static final ObjectMapper MAPPER = createMapper();

    static final String KEY_PREFIX = "cart:";
    static final String GUEST_PREFIX = "cart:guest:";

    /** 登录用户购物车 30 天 TTL，每次写操作都续期 */
    static final Duration CART_TTL = Duration.ofDays(30);

    /** 访客购物车 TTL：独立且更短，避免海量一次性 token 生成长期 key */
    static final Duration GUEST_TTL = Duration.ofDays(7);

    /** 单个购物车最多容纳的商品种类数（防止 HGETALL 变成大 key） */
    static final int MAX_SKU_TYPES = 100;

    /** 单个 SKU 最多件数，防止累加溢出与恶意下单 */
    static final int MAX_QUANTITY_PER_SKU = 200;

    private static final long LOCK_WAIT_SECONDS = 3L;
    private static final long LOCK_LEASE_SECONDS = 10L;

    /**
     * 单 key 写入脚本：先统计新增种类数做上限校验，再批量 HSET，最后续期 TTL。
     *
     * <p>
     * 只操作 KEYS[1] 一个 key —— 合并访客购物车时 userKey / guestKey 两个 key
     * 在 Redis Cluster 下不落在同一个 slot，多 key 脚本会直接抛 CROSSSLOT。
     *
     * <p>
     * ARGV: [1]=ttlSeconds [2]=maxTypes [3..]=field,value 对
     * 返回: 1 成功 / -1 超出种类上限
     */
    private static final DefaultRedisScript<Long> WRITE_SCRIPT = new DefaultRedisScript<>("""
            local ttl = tonumber(ARGV[1])
            local maxTypes = tonumber(ARGV[2])
            local newTypes = 0
            local i = 3
            while i < #ARGV do
                if redis.call('HEXISTS', KEYS[1], ARGV[i]) == 0 then
                    newTypes = newTypes + 1
                end
                i = i + 2
            end
            if redis.call('HLEN', KEYS[1]) + newTypes > maxTypes then
                return -1
            end
            i = 3
            while i < #ARGV do
                redis.call('HSET', KEYS[1], ARGV[i], ARGV[i + 1])
                i = i + 2
            end
            redis.call('EXPIRE', KEYS[1], ttl)
            return 1
            """, Long.class);

    private final StringRedisTemplate redis;
    private final RedissonClient redisson;

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.getFactory().configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        return mapper;
    }

    public CartService(StringRedisTemplate redis, RedissonClient redisson) {
        this.redis = redis;
        this.redisson = redisson;
    }

    // ============ 写操作 ============

    /** 加购：已存在则累加数量并保留原加购时间，不存在则新建。 */
    public CartItem addItem(Long userId, CartItem incoming) {
        CartItem item = normalize(incoming);
        String key = keyOf(userId);
        return withUserLock((userId),
                () -> addItem(key, CART_TTL.getSeconds(), fieldOf(item.skuId()), item));
    }

    /** 访客态加购，token 存在 Cookie 里 */
    public CartItem addGuestItem(String guestToken, CartItem incoming) {
        CartItem item = normalize(incoming);
        String guestIdentity = guestIdentityOf(guestToken);
        String guestKey = GUEST_PREFIX + guestIdentity;
        return withCartLock(guestLockName(guestIdentity),
                () -> addItem(guestKey, GUEST_TTL.getSeconds(), fieldOf(item.skuId()), item));
    }

    private CartItem addItem(String key, long ttlSeconds, String field, CartItem incoming) {
        String raw = (String) redis.opsForHash().get(key, field);
        if (raw == null) {
            writeOne(key, ttlSeconds, field, toJson(incoming));
            return incoming;
        }

        CartItem merged = fromJson(raw).plusQuantity(incoming.quantity());
        // 存量数据可能已经异常，累加后溢出成负数也要挡住
        if (merged.quantity() <= 0 || merged.quantity() > MAX_QUANTITY_PER_SKU) {
            throw new CartException("单个商品最多购买 " + MAX_QUANTITY_PER_SKU + " 件");
        }
        writeOne(key, ttlSeconds, field, toJson(merged));
        return merged;
    }

    /** 改数量：覆盖指定数量；数量 <= 0 视为删除该商品。 */
    public void updateQuantity(Long userId, String skuId, int quantity) {
        if (quantity <= 0) { // 边界校验留在锁外，别塞进 mutate
            removeItem(userId, skuId);
            return;
        }
        if (quantity > MAX_QUANTITY_PER_SKU) {
            throw new CartException("单个商品最多购买 " + MAX_QUANTITY_PER_SKU + " 件");
        }
        mutate(userId, skuId, old -> old.withQuantity(quantity));
    }

    /** 兼容旧调用方；SKU 本身按字符串保存。 */
    public void updateQuantity(Long userId, Long skuId, int quantity) {
        updateQuantity(userId, String.valueOf(Objects.requireNonNull(skuId, "skuId")), quantity);
    }

    /** 单个商品勾选切换 */
    public void setSelected(Long userId, String skuId, boolean selected) {
        mutate(userId, skuId, old -> old.withSelected(selected));
    }

    public void setSelected(Long userId, Long skuId, boolean selected) {
        setSelected(userId, String.valueOf(Objects.requireNonNull(skuId, "skuId")), selected);
    }

    /** 全选 / 全不选：批量覆盖写（不新增种类，脚本内的上限校验不会误报） */
    public void selectAll(Long userId, boolean selected) {
        String key = keyOf(userId);
        withUserLock(userId, () -> {
            Map<Object, Object> raw = redis.opsForHash().entries(key);
            if (raw.isEmpty()) {
                return;
            }
            Map<String, String> batch = new HashMap<>(raw.size() * 2);
            raw.forEach((f, v) -> {
                CartItem item = fromJson((String) v);
                batch.put((String) f, toJson(item.withSelected(selected)));
            });
            writeBatch(key, CART_TTL.getSeconds(), batch);
        });
    }

    public void removeItem(Long userId, String skuId) {
        String key = keyOf(userId);
        String field = fieldOf(skuId);
        withUserLock((userId), () -> redis.opsForHash().delete(key, field));
    }

    public void removeItem(Long userId, Long skuId) {
        removeItem(userId, String.valueOf(Objects.requireNonNull(skuId, "skuId")));
    }

    public void clear(Long userId) {
        String key = keyOf(userId);
        withUserLock((userId), () -> redis.delete(key));
    }

    // ============ 读操作 ============

    /** 渲染购物车：按加购时间倒序（最新加购在前），同毫秒按 skuId 保证稳定序 */
    public List<CartItem> list(Long userId) {
        return list(keyOf(userId));
    }

    public List<CartItem> listGuest(String guestToken) {
        if (guestToken == null || guestToken.isBlank()) {
            return Collections.emptyList();
        }
        return list(GUEST_PREFIX + guestIdentityOf(guestToken));
    }

    @SuppressWarnings("null")
    private List<CartItem> list(String key) {
        Map<Object, Object> raw = redis.opsForHash().entries(key);
        if (raw.isEmpty()) {
            return Collections.emptyList();
        }
        List<CartItem> items = new ArrayList<>(raw.size());
        for (Object v : raw.values()) {
            items.add(fromJson((String) v));
        }
        items.sort(Comparator.comparingLong(CartItem::addedAt).reversed()
                .thenComparing((left, right) -> left.skuId().compareTo(right.skuId())));
        return items;
    }

    public Optional<CartItem> get(Long userId, String skuId) {
        return Optional.ofNullable(readOne(keyOf(userId), fieldOf(skuId)));
    }

    public Optional<CartItem> get(Long userId, Long skuId) {
        return get(userId, String.valueOf(Objects.requireNonNull(skuId, "skuId")));
    }

    /** 已勾选商品的总金额 */
    public BigDecimal total(Long userId) {
        BigDecimal sum = BigDecimal.ZERO;
        for (CartItem item : list(userId)) {
            if (item.selected()) {
                sum = sum.add(item.subtotal());
            }
        }
        return sum;
    }

    /** 商品总件数（不是种类数） */
    public int totalQuantity(Long userId) {
        int n = 0;
        for (CartItem item : list(userId)) {
            n += item.quantity();
        }
        return n;
    }

    /** 商品种类数，直接用 HLEN，O(1) */
    public long typeCount(Long userId) {
        Long size = redis.opsForHash().size(keyOf(userId));
        return size == null ? 0L : size;
    }

    // ============ 访客购物车合并 ============

    /**
     * 登录后把访客购物车合并进用户购物车：同 SKU 数量累加、保留较早的加购时间。
     * 合并完成后删除访客 key。
     *
     * <p>
     * 这里持有 user 锁 + guest 锁，写用户购物车用单 key 脚本，删访客 key 是独立命令，
     * 因此不存在 Cluster 下的 CROSSSLOT 问题。代价是「写用户」与「删访客」不再是一个
     * 原子步骤：删除失败时以 error 日志告警并抛异常，由调用方决定是否重试。
     *
     * @return 合并进来的商品种类数
     */
    public int mergeGuestCart(String guestToken, Long userId) {
        String userKey = keyOf(userId);
        if (guestToken == null || guestToken.isBlank()) {
            return 0;
        }
        String guestIdentity = guestIdentityOf(guestToken);
        String guestKey = GUEST_PREFIX + guestIdentity;
        return withUserLock((userId), () -> withCartLock(guestLockName(guestIdentity), () -> {
            Map<Object, Object> guestRaw = redis.opsForHash().entries(guestKey);
            if (guestRaw.isEmpty()) {
                return 0;
            }
            // 一次 HGETALL 拿到用户侧全量，避免逐条 HGET 的 N 次往返
            Map<Object, Object> userRaw = redis.opsForHash().entries(userKey);

            Map<String, String> updates = new HashMap<>(guestRaw.size() * 2);
            int merged = 0;
            for (Map.Entry<Object, Object> e : guestRaw.entrySet()) {
                String field = (String) e.getKey();
                CartItem guestItem = fromJson((String) e.getValue());
                if (guestItem.quantity() <= 0) {
                    continue;
                }
                String existingRaw = (String) userRaw.get(field);
                if (existingRaw == null) {
                    updates.put(field, toJson(guestItem));
                } else {
                    CartItem userItem = fromJson(existingRaw);
                    long sum = (long) userItem.quantity() + guestItem.quantity();
                    int quantity = sum > MAX_QUANTITY_PER_SKU ? MAX_QUANTITY_PER_SKU : (int) sum;
                    CartItem combined = new CartItem(
                            userItem.skuId(),
                            userItem.productId(),
                            userItem.title(),
                            userItem.image(),
                            userItem.price(),
                            quantity,
                            userItem.selected() || guestItem.selected(),
                            Math.min(userItem.addedAt(), guestItem.addedAt()));
                    updates.put(field, toJson(combined));
                }
                merged++;
            }

            writeBatch(userKey, CART_TTL.getSeconds(), updates);

            if (!Boolean.TRUE.equals(redis.delete(guestKey))) {
                log.error("访客购物车合并后清理失败，需人工确认是否会重复合并, guestKey={}", guestKey);
                throw new CartException("访客购物车清理失败，请稍后重试");
            }
            return merged;
        }));
    }

    // ============ 内部辅助 ============

    private <T> T withUserLock(Long userId, Supplier<T> action) {
        return withCartLock(userLockName(userId), action);
    }

    private void withUserLock(Long userId, Runnable action) {
        withCartLock(userLockName(userId), action);
    }

    /** 单个商品的读-改-写模板：商品不存在则静默忽略 */
    private void mutate(Long userId, String skuId, UnaryOperator<CartItem> mutator) {
        String key = keyOf(userId);
        String field = fieldOf(skuId);
        withUserLock((userId), () -> {
            CartItem old = readOne(key, field);
            if (old != null) {
                writeOne(key, CART_TTL.getSeconds(), field, toJson(mutator.apply(old)));
            }
        });
    }

    /**
     * 入参归一化：skuId 非空、数量为正、价格非空非负，缺失的加购时间由服务端生成。
     * 加购时间不能信任客户端，否则排序会被伪造的时间戳打乱。
     */
    private static CartItem normalize(CartItem incoming) {
        Objects.requireNonNull(incoming, "incoming");
        fieldOf(incoming.skuId());
        if (incoming.quantity() <= 0) {
            throw new CartException("加购数量必须大于 0");
        }
        if (incoming.quantity() > MAX_QUANTITY_PER_SKU) {
            throw new CartException("单个商品最多购买 " + MAX_QUANTITY_PER_SKU + " 件");
        }
        BigDecimal price = incoming.price();
        if (price == null || price.signum() < 0) {
            throw new CartException("商品价格不能为空或负数");
        }
        if (incoming.addedAt() > 0) {
            return incoming;
        }
        return new CartItem(incoming.skuId(), incoming.productId(), incoming.title(),
                incoming.image(), price, incoming.quantity(), incoming.selected(),
                System.currentTimeMillis());
    }

    private void writeOne(String key, long ttlSeconds, String field, String value) {
        writeBatch(key, ttlSeconds, Map.of(field, value));
    }

    private void writeBatch(String key, long ttlSeconds, Map<String, String> batch) {
        if (batch.isEmpty()) {
            return;
        }
        List<String> args = new ArrayList<>(2 + batch.size() * 2);
        args.add(String.valueOf(ttlSeconds));
        args.add(String.valueOf(MAX_SKU_TYPES));
        batch.forEach((field, value) -> {
            args.add(field);
            args.add(value);
        });
        Long result = redis.execute(WRITE_SCRIPT, List.of(key), args.toArray());
        checkWriteResult(result);
    }

    private static void checkWriteResult(Long result) {
        if (result == null) {
            throw new CartException("购物车写入失败");
        }
        if (result == -1L) {
            throw new CartException("购物车最多放 " + MAX_SKU_TYPES + " 种商品");
        }
    }

    private static String guestIdentityOf(String guestToken) {
        if (guestToken == null || guestToken.isBlank()) {
            throw new IllegalArgumentException("guestToken must not be blank");
        }
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(guestToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    private static String userLockName(Long userId) {
        return "lock:cart:user:" + Objects.requireNonNull(userId, "userId");
    }

    private static String guestLockName(String guestIdentity) {
        return "lock:cart:guest:" + guestIdentity;
    }

    private static String keyOf(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be a positive number");
        }
        return KEY_PREFIX + userId;
    }

    private static String fieldOf(String skuId) {
        if (skuId == null || skuId.isBlank()) {
            throw new IllegalArgumentException("skuId must not be blank");
        }
        return skuId;
    }

    /**
     * 固定租约的分布式锁。
     * 不用 watchdog 续租：购物车操作都是毫秒级，线程若卡死（长 GC、慢查询、死锁），
     * watchdog 会一直帮它续租，锁反而永远不释放；固定租约最多残留 LEASE 秒。
     */
    private <T> T withCartLock(String lockName, Supplier<T> action) {
        RLock lock = redisson.getLock(lockName);
        boolean acquired = false;
        try {
            acquired = lock.tryLock(LOCK_WAIT_SECONDS, LOCK_LEASE_SECONDS, TimeUnit.SECONDS);
            if (!acquired) {
                throw new CartException("系统繁忙，请稍后重试");
            }
            return action.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CartException("购物车操作被中断", e);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                } catch (IllegalMonitorStateException e) {
                    // 租约已过期并被其他线程抢占，此处不能让解锁异常覆盖业务结果
                    log.warn("购物车锁租约已过期, lock={}", lockName, e);
                }
            }
        }
    }

    private void withCartLock(String lockName, Runnable action) {
        withCartLock(lockName, () -> {
            action.run();
            return null;
        });
    }

    private CartItem readOne(String key, String field) {
        String raw = (String) redis.opsForHash().get(key, field);
        return raw == null ? null : fromJson(raw);
    }

    private static String toJson(CartItem item) {
        try {
            return MAPPER.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new CartException("购物车序列化失败", e);
        }
    }

    private static CartItem fromJson(String json) {
        try {
            CartItem item = MAPPER.readValue(json, CartItem.class);
            if (item == null) {
                throw new CartException("购物车数据损坏，无法读取");
            }
            return item;
        } catch (JsonProcessingException e) {
            throw new CartException("购物车数据损坏，无法读取", e);
        }
    }

}
