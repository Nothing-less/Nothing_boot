package mu.nothingless.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BcryptUtil {

    
    // strength >= 12 , 显式指定 $2y 版本
    private final BCryptPasswordEncoder encoder;

    public BcryptUtil() {
        this.encoder = new BCryptPasswordEncoder(
            BCryptPasswordEncoder.BCryptVersion.$2Y, 
            12
        );
    }

    /**
     * 密码哈希
     */
    public String encode(String rawStr) {
        if (rawStr == null || rawStr.isBlank()) {
            throw new IllegalArgumentException("Empty Value");
        }
        return encoder.encode(rawStr);
    }

    /**
     * 密码校验
     */
    public boolean matches(String rawStr, String encodedStr) {
        if (rawStr == null || encodedStr == null) {
            log.warn("Verification Failed: Null Value");
            return false;
        }
        boolean result = encoder.matches(rawStr, encodedStr);
        if (!result) {
            log.debug("Verification Failed!");
        }
        return result;
    }
}