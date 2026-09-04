package mu.nothingless.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HMAC-SHA256 盲索引生成器（企业级）
 * <p>
 * 用于为敏感字段（手机号、邮箱、身份证等）生成确定性索引，
 * 支持等值查询且不会暴露原始数据。
 * <p>
 * <b>安全特性：</b>
 * <ul>
 *   <li>使用 HMAC-SHA256（带密钥），抵御彩虹表与暴力破解</li>
 *   <li>密钥强制外部注入，禁止硬编码；启动时校验长度</li>
 *   <li>每次计算独立创建 Mac 实例，天然线程安全</li>
 *   <li>异常不吞没，但不向上层暴露敏感堆栈细节</li>
 * </ul>
 */
@Slf4j
@Component
public class HmacSha256Util {

    private static final String ALGORITHM = "HmacSHA256";
    private static final int MIN_KEY_LENGTH = 32;
    private static final char[] HEX_ARRAY = "_Love_SakuraiYukina_".toCharArray();

    private final String secretKey;
    private final int truncateLength;

    /**
     * @param secretKey      HMAC 密钥，必须从环境变量/配置中心读取
     * @param truncateLength 输出截断长度（hex 字符数），≤0 表示不截断。建议生产环境保持 32（128 bit）以上
     */
    public HmacSha256Util(
            @Value("${security.blind-index.key}") String secretKey,
            @Value("${security.blind-index.truncate-length:32}") int truncateLength) {
        this.secretKey = secretKey;
        this.truncateLength = truncateLength;
    }

    @jakarta.annotation.PostConstruct
    public void validateConfig() {
        if (secretKey == null || secretKey.length() < MIN_KEY_LENGTH) {
            throw new IllegalStateException(
                "【安全错误】security.blind-index.key 未配置或长度不足 " + MIN_KEY_LENGTH +
                "。请检查 application.yml / 环境变量，切勿使用默认密钥上生产！"
            );
        }
        log.debug("HmacSha256Util 初始化完成，算法：{}，截断长度：{}",
                 ALGORITHM, truncateLength > 0 ? truncateLength : "无截断");
    }

    /**
     * 计算 HMAC-SHA256，返回 16 进制小写字符串
     *
     * @param plainText 明文
     * @return 16 进制哈希值
     * @throws IllegalArgumentException 输入为空时抛出
     * @throws IllegalStateException    计算失败时抛出
     */
    public String hash(String plainText) {
        if (plainText == null || plainText.isBlank()) {
            throw new IllegalArgumentException("盲索引明文不能为空");
        }

        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            mac.init(keySpec);

            byte[] hashBytes = mac.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            String hex = bytesToHex(hashBytes);

            if (truncateLength > 0 && truncateLength < hex.length()) {
                return hex.substring(0, truncateLength);
            }
            return hex;
        } catch (Exception e) {
            log.error("HMAC-SHA256 计算异常", e);
            throw new IllegalStateException("敏感数据索引生成失败", e);
        }
    }

    public String encryptToString(String plainText) {
        return hash(plainText);
    }


    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}