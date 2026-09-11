package mu.nothingless.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.advice.RetResult;
import mu.nothingless.exceptions.TokenException;
import mu.nothingless.security.UserContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper; // 注入 ObjectMapper

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws ServletException, IOException {

        try {
            // 已经有认证信息
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                chain.doFilter(req, res);
                return;
            }

            // 没有 Token 头（留给后续安全规则处理）
            String header = req.getHeader(jwtProperties.getHeader());
            if (!StringUtils.hasText(header) || !header.startsWith(jwtProperties.getPrefix())) {
                chain.doFilter(req, res);
                return;
            }

            // 解析并校验 Token
            String token = header.substring(jwtProperties.getPrefix().length());
            Claims claims = jwtUtil.parseAccessToken(token);

            String userId = JwtUtil.getUserId(claims);
            UserDetails userDetails = User.builder()
                    .username(userId)
                    .password("")
                    .authorities(Collections.emptyList())
                    .build();

            var auth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserContext.setCurrentUserId(userId);

            // 在 UserContext 仍然有效时，继续走后续 Filter
            chain.doFilter(req, res);

        } catch (ExpiredJwtException e) {
            writeUnauthorized(res, "Token 已过期: " + e.getLocalizedMessage());
        } catch (JwtException | IllegalArgumentException e) {
            writeUnauthorized(res, "Token 无效: " + e.getLocalizedMessage());
        } catch (TokenException e) {
            if (e.getMessage() != null && e.getMessage().contains("过期")) {
                writeUnauthorized(res, "Token 已过期: " + e.getLocalizedMessage());
            } else {
                writeUnauthorized(res, "Token 无效: " + e.getLocalizedMessage());
            }
        } finally {
            // 请求处理完毕后清理 ThreadLocal，防止内存泄漏
            UserContext.clear();
        }
    }

    /**
     * 401 JSON 响应
     */
    private void writeUnauthorized(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(res.getOutputStream(), RetResult.unauthorized(message));
    }
}
