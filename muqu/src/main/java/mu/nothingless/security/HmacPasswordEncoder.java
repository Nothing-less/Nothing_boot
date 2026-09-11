package mu.nothingless.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mu.nothingless.utils.HmacSha256Util;

@Component
@RequiredArgsConstructor
public class HmacPasswordEncoder implements PasswordEncoder {

    private final HmacSha256Util hmacSha256Util;

    @Override
    public String encode(CharSequence rawPassword) {
        return hmacSha256Util.encryptToString(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null) return false;
        byte[] a = hmacSha256Util.encryptToString(rawPassword.toString()).getBytes(StandardCharsets.UTF_8);
        byte[] b = storedPassword.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(a, b);   // 定长 时间比较，防时序侧信道
    }
}
