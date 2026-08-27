package mu.nothingless.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import mu.nothingless.utils.AesGcmUtil;

@Slf4j
@Configuration
public class AesCryptoConfig {

    @Bean
    AesGcmUtil aes_init(@Value("${aes.aes-key-base64}") String base64Key) {
        try {
            return AesGcmUtil.fromBase64Key(base64Key);
        } catch (IllegalArgumentException e) {
            log.error("AES 密钥格式错误，请检查 aes.aes-key-base64 配置", e);
            throw new BeanCreationException("AES 密钥格式错误", e);
        }
    }
}

// $env:AES_KEY_B64= "1uk28UMcwhKFq+s67RVMwsvBuMNhVWpKm60IllxhDm4="