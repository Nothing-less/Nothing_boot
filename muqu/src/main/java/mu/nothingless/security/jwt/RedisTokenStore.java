package mu.nothingless.security.jwt;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void storeRefreshToken(String userId, String jti, Duration ttl) {
        redisTemplate.opsForValue().set(RedisKeys.refreshToken(userId, jti), "1", ttl);
    }

    @Override
    public boolean validateRefreshToken(String userId, String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.refreshToken(userId, jti)));
    }

    @Override
    public void removeRefreshToken(String userId, String jti) {
        redisTemplate.delete(RedisKeys.refreshToken(userId, jti));
    }

    @Override
    public void blacklistAccessToken(String jti, Duration ttl) {
        redisTemplate.opsForValue().set(RedisKeys.accessTokenBlacklist(jti), "1", ttl);
    }

    @Override
    public boolean isAccessTokenBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.accessTokenBlacklist(jti)));
    }

    @Override
    public void storeAccessToken(String userId, String jti, Duration ttl) {
        redisTemplate.opsForValue().set(RedisKeys.accessToken(userId, jti), "1", ttl);

        String indexKey = RedisKeys.userAccessTokens(userId);
        redisTemplate.opsForSet().add(indexKey, jti);
        redisTemplate.expire(indexKey, ttl.plusMinutes(10));
    }

    @Override
    public void removeAccessToken(String userId, String jti) {
        redisTemplate.delete(RedisKeys.accessToken(userId, jti));
        redisTemplate.opsForSet().remove(RedisKeys.userAccessTokens(userId), jti);
    }

    @Override
    public Set<String> getUserAccessTokens(String userId) {
        Set<String> members = redisTemplate.opsForSet().members(RedisKeys.userAccessTokens(userId));
        return members != null ? members : Set.of();
    }

    @Override
    public void clearUserTokens(String userId) {
        // 1. 拉黑并清理所有 Access Token
        Set<String> accessJtis = getUserAccessTokens(userId);
        if (accessJtis != null) {
            for (String jti : accessJtis) {
                // 不知道剩余 TTL，给 1 天黑名单兜底
                blacklistAccessToken(jti, Duration.ofDays(1));
                redisTemplate.delete(RedisKeys.accessToken(userId, jti));
            }
        }
        redisTemplate.delete(RedisKeys.userAccessTokens(userId));

        // 2. 清理所有 Refresh Token
        Set<String> refreshKeys = redisTemplate.keys(RedisKeys.refreshTokenPattern(userId));
        if (refreshKeys != null && !refreshKeys.isEmpty()) {
            redisTemplate.delete(refreshKeys);
        }

        log.info("RedisTokenStore 清理用户全部令牌 | userId={}", userId);
    }
}
