package mu.nothingless.security.cache;

// mu.nothingless.security.jwt.RedisKeys
public final class RedisKeys {
    private static final String PREFIX = "auth";

    private RedisKeys() {}

    public static String refreshToken(String userId, String jti) {
        return String.format("%s:refresh:%s:%s", PREFIX, userId, jti);
    }

    public static String refreshTokenPattern(String userId) {
        return String.format("%s:refresh:%s:*", PREFIX, userId);
    }

    public static String accessTokenBlacklist(String jti) {
        return String.format("%s:blacklist:%s", PREFIX, jti);
    }

    public static String accessToken(String userId, String jti) {
        return String.format("%s:access:%s:%s", PREFIX, userId, jti);
    }

    public static String userAccessTokens(String userId) {
        return String.format("%s:access:index:%s", PREFIX, userId);
    }
}