package mu.nothingless.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mu.nothingless.exceptions.BusinessException;

@Service
@RequiredArgsConstructor
public class LoginSecurityService {

    private final StringRedisTemplate redisTemplate;
    private static final int ACCOUNT_MAX_FAIL = 5;
    private static final int IP_MAX_FAIL = 10;
    private static final Duration ACCOUNT_LOCK_TTL = Duration.ofMinutes(30);
    private static final Duration IP_LOCK_TTL = Duration.ofMinutes(60);

    /** 检查是否被锁定 */
    public void checkLoginLock(String userAccount, String clientIp) {
        String accountLockKey = "login_lock:account:" + userAccount;
        String ipLockKey = "login_lock:ip:" + clientIp;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(accountLockKey))) {
            Long ttl = redisTemplate.getExpire(accountLockKey);
            throw new BusinessException("账号已被锁定，请 " + ttl + " 秒后重试");
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey(ipLockKey))) {
            throw new BusinessException("当前IP登录过于频繁，请稍后再试");
        }
    }

    /** 记录失败次数 */
    public void recordLoginFail(String userAccount, String clientIp) {
        // 账号维度
        String accountFailKey = "login_fail:account:" + userAccount;
        Long accountFailCount = redisTemplate.opsForValue().increment(accountFailKey);
        redisTemplate.expire(accountFailKey, Duration.ofMinutes(15));

        if (accountFailCount != null && accountFailCount >= ACCOUNT_MAX_FAIL) {
            redisTemplate.opsForValue().set(
                "login_lock:account:" + userAccount, "1", ACCOUNT_LOCK_TTL);
        }

        // IP 维度（更宽松或更严格都可以调）
        String ipFailKey = "login_fail:ip:" + clientIp;
        Long ipFailCount = redisTemplate.opsForValue().increment(ipFailKey);
        redisTemplate.expire(ipFailKey, Duration.ofMinutes(10));

        if (ipFailCount != null && ipFailCount >= IP_MAX_FAIL) {
            redisTemplate.opsForValue().set(
                "login_lock:ip:" + clientIp, "1", IP_LOCK_TTL);
        }
    }

    /** 登录成功，清除所有失败记录 */
    public void clearLoginFail(String userAccount, String clientIp) {
        redisTemplate.delete("login_fail:account:" + userAccount);
        redisTemplate.delete("login_fail:ip:" + clientIp);
        redisTemplate.delete("login_lock:account:" + userAccount);
        redisTemplate.delete("login_lock:ip:" + clientIp);
    }
}
