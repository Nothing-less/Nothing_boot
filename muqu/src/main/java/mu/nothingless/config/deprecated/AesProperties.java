package mu.nothingless.config.deprecated;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Deprecated
/*
@Component
@ConfigurationProperties(prefix = "aes")
@Validated
@Data
 */
public class AesProperties {

    /**
     * 仅作配置展示，当前实现固定使用 AES/GCM/NoPadding。
     * 如需切换模式，需同步修改 AesUtil 的 TRANSFORMATION 与参数逻辑。
     */
    @NotBlank
    private String algorithm = "AES/GCM/NoPadding";

    @NotBlank
    private String transformation = "AES/GCM/NoPadding";

    /**
     * AES key：建议配置 32 个纯 ASCII 字符（如字母+数字），对应 AES-256。
     * 注意：@Size 校验的是字符数，请确保 UTF-8 字节长度等于 16/24/32。
     */
    @NotBlank
    @Size(min = 16, max = 32)
    private String key;

    @PostConstruct
    public void init() {
        // Spring 启动时自动将配置 key 注入静态工具类
        // AesUtil.init(key);
    }
}