package mu.nothingless.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * HMAC 密钥（Base64 编码）。HS256/384/512 时必填。
     */
    @NotBlank(message = "JWT 密钥不能为空")
    private String secret;

    /**
     * 非对称私钥（Base64 编码的 DER）。RS* / ES* 时必填。
     */
    private String privateKey;
    
    /**
     * 非对称公钥（Base64 编码的 DER）。RS* / ES* 时必填。
     */
    private String publicKey;

    /** Access Token 过期时间，默认 1 小时 */
    private Duration accessTtl = Duration.ofHours(1);

    /** Refresh Token 过期时间，默认 7 天 */
    private Duration refreshTtl = Duration.ofDays(7);

    private Algorithm algorithm = Algorithm.HS512;

    /** Token 请求头名称 */
    private String header = "Authorization";

    /** Token 前缀 */
    private String prefix = "Bearer ";

    /** Redis 异常时是否强制拒绝（false 则仅验签，降级通过） */
    private boolean redisCheckEnabled = true;

    /** 是否在 Redis 中记录 Access Token（支持查看在线设备 / 单点登录） */
    private boolean accessTokenStoreEnabled = true;

    /** 是否只允许单设备同时在线 */
    private boolean singleDeviceLoginEnabled = false;

    public enum Algorithm {
        HS256, HS384, HS512,
        RS256, RS384, RS512,
        ES256, ES384, ES512
    }

}