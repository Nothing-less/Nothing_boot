package mu.nothingless.security.jwt;

import java.time.Duration;
import java.util.Set;

public interface TokenStore {

    void storeRefreshToken(String userId, String jti, Duration ttl);
    boolean validateRefreshToken(String userId, String jti);
    void removeRefreshToken(String userId, String jti);

    void blacklistAccessToken(String jti, Duration ttl);
    boolean isAccessTokenBlacklisted(String jti);

    void storeAccessToken(String userId, String jti, Duration ttl);
    void removeAccessToken(String userId, String jti);
    Set<String> getUserAccessTokens(String userId);

    /** 清理某用户的所有 Access + Refresh Token（用于单设备登录 / 全设备登出） */
    void clearUserTokens(String userId);
}
