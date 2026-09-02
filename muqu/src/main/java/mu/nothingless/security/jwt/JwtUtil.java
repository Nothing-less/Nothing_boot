// mu.nothingless.security.jwt.JwtUtil
package mu.nothingless.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.exception.TokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperties props;
    private final TokenStore tokenStore;

    private final Key signingKey;
    private final Key verifyingKey;
    private final SecureDigestAlgorithm<?, ?> signatureAlgorithm;
    private final JwtParser jwtParser;

    public JwtUtil(JwtProperties props, TokenStore tokenStore) {
        this.props = props;
        this.tokenStore = tokenStore;
        this.signatureAlgorithm = resolveAlgorithm(props.getAlgorithm());

        KeyPairResult kp = initKeys(props);
        this.signingKey = kp.signing();
        this.verifyingKey = kp.verifying();

        JwtParserBuilder parserBuilder = Jwts.parser();
        if (verifyingKey instanceof SecretKey sk) {
            parserBuilder.verifyWith(sk);
        } else if (verifyingKey instanceof PublicKey pk) {
            parserBuilder.verifyWith(pk);
        }
        this.jwtParser = parserBuilder.build();
    }

    @PostConstruct
    public void validateConfig() {
        if (props.getAlgorithm().name().startsWith("HS")) {
            if (!StringUtils.hasText(props.getSecret())) {
                throw new IllegalStateException("HMAC 算法必须配置 jwt.secret");
            }
        } else {
            if (!StringUtils.hasText(props.getPrivateKey()) || !StringUtils.hasText(props.getPublicKey())) {
                throw new IllegalStateException("非对称算法必须配置 jwt.private-key 和 jwt.public-key");
            }
        }
        log.info("JwtUtil 配置校验通过 | algorithm={} | accessTtl={} | refreshTtl={}",
                props.getAlgorithm(), props.getAccessTtl(), props.getRefreshTtl());
    }

    // ==================== 生成 Token ====================

    /**
     * 生成全新的 Access + Refresh 令牌对。
     * 若开启单设备登录，会自动踢掉该用户所有旧令牌。
     */
    public TokenPair generateToken(String userId, String userAccount, Map<String, Object> extra) {
        if (props.isSingleDeviceLoginEnabled()) {
            tokenStore.clearUserTokens(userId);
            log.info("单设备登录模式：清理用户历史令牌 | userId={}", userId);
        }

        String accessJti = UUID.randomUUID().toString();
        String refreshJti = UUID.randomUUID().toString();

        String accessToken = buildToken(accessJti, userId, userAccount, TokenType.ACCESS, extra);
        String refreshToken = buildToken(refreshJti, userId, userAccount, TokenType.REFRESH, null);

        tokenStore.storeRefreshToken(userId, refreshJti, props.getRefreshTtl());

        if (props.isAccessTokenStoreEnabled()) {
            tokenStore.storeAccessToken(userId, accessJti, props.getAccessTtl());
        }

        log.info("TokenPair 签发 | userId={} | accessJti={} | refreshJti={}", userId, accessJti, refreshJti);
        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * Refresh Token Rotation：用 Refresh Token 换取全新令牌对，旧 Refresh Token 立即失效。
     * @throws TokenException 
     */
    public TokenPair refreshToken(String refreshToken) throws TokenException {
        Claims claims = parseRefreshToken(refreshToken);
        String userId = claims.getSubject();
        String userAccount = getUserAccount(claims);
        String oldJti = claims.getId();

        // 旧 Refresh Token 立即作废
        tokenStore.removeRefreshToken(userId, oldJti);
        log.info("Refresh Token 旋转 | userId={} | oldJti={}", userId, oldJti);

        return generateToken(userId, userAccount, null);
    }

    // ==================== 解析 Token ====================

    public Claims parseAccessToken(String token) throws TokenException {
        Claims claims = parseToken(token);

        if (!TokenType.ACCESS.name().equals(claims.get(TokenClaims.TYPE))) {
            throw new TokenException("Token 类型错误：要求 Access Token");
        }

        String jti = claims.getId();
        if (tokenStore.isAccessTokenBlacklisted(jti)) {
            throw new TokenException("Token 已被注销");
        }

        return claims;
    }

    public Claims parseRefreshToken(String token) throws TokenException {
        Claims claims = parseToken(token);

        if (!TokenType.REFRESH.name().equals(claims.get(TokenClaims.TYPE))) {
            throw new TokenException("Token 类型错误：要求 Refresh Token");
        }

        if (props.isRedisCheckEnabled()) {
            String userId = claims.getSubject();
            String jti = claims.getId();
            if (!tokenStore.validateRefreshToken(userId, jti)) {
                throw new TokenException("Refresh Token 已失效或不存在");
            }
        }

        return claims;
    }

    // ==================== 注销 / 撤销 ====================

    /**
     * 注销指定令牌。Access Token 进黑名单，Refresh Token 从 Redis 删除。
     */
    public void logout(String accessToken, String refreshToken) {
        if (StringUtils.hasText(accessToken)) {
            try {
                Claims claims = parseAccessToken(accessToken);
                String jti = claims.getId();
                long remain = claims.getExpiration().getTime() - System.currentTimeMillis();
                if (remain > 0) {
                    tokenStore.blacklistAccessToken(jti, Duration.ofMillis(remain));
                }
                if (props.isAccessTokenStoreEnabled()) {
                    tokenStore.removeAccessToken(claims.getSubject(), jti);
                }
                log.info("Access Token 注销 | jti={} | userId={}", jti, claims.getSubject());
            } catch (TokenException e) {
                log.warn("注销时 Access Token 解析失败，已忽略 | reason={}", e.getMessage());
            }
        }

        if (StringUtils.hasText(refreshToken)) {
            try {
                Claims claims = parseRefreshToken(refreshToken);
                String userId = claims.getSubject();
                String jti = claims.getId();
                tokenStore.removeRefreshToken(userId, jti);
                log.info("Refresh Token 注销 | jti={} | userId={}", jti, userId);
            } catch (TokenException e) {
                log.warn("注销时 Refresh Token 解析失败，已忽略 | reason={}", e.getMessage());
            }
        }
    }

    /** 强制某用户所有设备登出（后台管理 / 修改密码后调用） */
    public void logoutAllDevices(String userId) {
        tokenStore.clearUserTokens(userId);
        log.info("用户全设备强制登出 | userId={}", userId);
    }

    // ==================== 辅助方法 ====================

    public static String getUserId(Claims claims) {
        return claims.getSubject();
    }

    public static String getUserAccount(Claims claims) {
        return claims.get(TokenClaims.USER_ACCOUNT, String.class);
    }

    public static String getTokenId(Claims claims) {
        return claims.getId();
    }

    public static boolean isRefreshToken(Claims claims) {
        return TokenType.REFRESH.name().equals(claims.get(TokenClaims.TYPE));
    }

    // ==================== 私有方法 ====================

    private String buildToken(String jti, String userId, String userAccount, TokenType type, Map<String, Object> extra) {
        Date now = new Date();
        Duration ttl = (type == TokenType.ACCESS) ? props.getAccessTtl() : props.getRefreshTtl();
        Date expiry = new Date(now.getTime() + ttl.toMillis());

        JwtBuilder builder = Jwts.builder()
                .id(jti)
                .subject(userId)
                .claim(TokenClaims.USER_ACCOUNT, userAccount)
                .claim(TokenClaims.TYPE, type.name())
                .issuedAt(now)
                .notBefore(now)
                .expiration(expiry);

        if (extra != null) {
            extra.forEach(builder::claim);
        }

        if (signingKey instanceof SecretKey sk) {
            builder.signWith(sk);
        } else if (signingKey instanceof PrivateKey pk) {
            builder.signWith(pk);
        }

        return builder.compact();
    }

    public Claims parseToken(String token) throws TokenException {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期 | jti={}", e.getClaims().getId());
            throw new TokenException("登录已过期，请重新登录");
        } catch (JwtException e) {
            log.warn("Token 解析失败: {}", e.getMessage());
            throw new TokenException("无效的登录凭证");
        }
    }

    private KeyPairResult initKeys(JwtProperties props) {
        return switch (props.getAlgorithm()) {
            case HS256, HS384, HS512 -> {
                SecretKey key = KeyLoader.loadHmacKey(props.getSecret());
                yield new KeyPairResult(key, key);
            }
            case RS256, RS384, RS512 -> {
                KeyPair kp = KeyLoader.loadRsaKeyPair(props.getPrivateKey(), props.getPublicKey());
                yield new KeyPairResult(kp.getPrivate(), kp.getPublic());
            }
            case ES256, ES384, ES512 -> {
                KeyPair kp = KeyLoader.loadEcKeyPair(props.getPrivateKey(), props.getPublicKey());
                yield new KeyPairResult(kp.getPrivate(), kp.getPublic());
            }
        };
    }

    private SecureDigestAlgorithm<?, ?> resolveAlgorithm(JwtProperties.Algorithm alg) {
        return switch (alg) {
            case HS256 -> Jwts.SIG.HS256;
            case HS384 -> Jwts.SIG.HS384;
            case HS512 -> Jwts.SIG.HS512;
            case RS256 -> Jwts.SIG.RS256;
            case RS384 -> Jwts.SIG.RS384;
            case RS512 -> Jwts.SIG.RS512;
            case ES256 -> Jwts.SIG.ES256;
            case ES384 -> Jwts.SIG.ES384;
            case ES512 -> Jwts.SIG.ES512;
        };
    }

    private record KeyPairResult(Key signing, Key verifying) {}

    public record TokenPair(String accessToken, String refreshToken) {}
}