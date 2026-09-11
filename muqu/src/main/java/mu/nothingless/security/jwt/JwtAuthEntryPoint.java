package mu.nothingless.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mu.nothingless.advice.RetResult;

@Component
@RequiredArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res,
            AuthenticationException authException) throws IOException {

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 无论 authException 是什么，都写入响应体
        String message = resolveMessage(authException);

        objectMapper.writeValue(res.getOutputStream(),
                RetResult.unauthorized(message));
    }

    /**
     * 统一解析异常消息
     */
    private String resolveMessage(AuthenticationException authException) {
        if (authException == null) {
            return "认证失败";
        }

        // 异常本身类型判断
        if (authException instanceof CredentialsExpiredException) {
            return "Token 已过期(CredentialsExpiredException)";
        }
        if (authException instanceof BadCredentialsException) {
            return "Token 无效(BadCredentialsException)";
        }

        // 遍历 cause 链
        Throwable cause = authException.getCause();
        while (cause != null && cause != cause.getCause()) {
            if (cause instanceof CredentialsExpiredException) {
                return "Token 已过期(CredentialsExpiredException)";
            }
            if (cause instanceof BadCredentialsException) {
                return "Token 无效(BadCredentialsException)";
            }
            cause = cause.getCause();
        }

        // 根据消息内容判断
        String msg = authException.getMessage();
        if (StringUtils.hasText(msg)) {
            if (msg.contains("过期")) {
                return "Token 已过期: \n" + msg;
            }
            if (msg.contains("无效")) {
                return "Token 无效: \n" + msg;
            }
            return msg;
        }

        return "认证失败";
    }
}