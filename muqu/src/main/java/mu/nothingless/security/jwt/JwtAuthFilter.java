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
import mu.nothingless.exception.TokenException;
import mu.nothingless.security.UserContext;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader(jwtProperties.getHeader());

        if (!StringUtils.hasText(header) || !header.startsWith(jwtProperties.getPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.substring(jwtProperties.getPrefix().length());

        try {
            // 解析 Token
            Claims claims = jwtUtil.parseToken(token);

            // 拒绝 Refresh Token 访问业务接口
            if (JwtUtil.isRefreshToken(claims)) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                throw new AccessDeniedException("Refresh Token 不能用于业务访问");
            }

            // 构建 Spring Security 上下文
            String userId = JwtUtil.getUserId(claims);
            UserDetails userDetails = User.builder()
                    .username(userId)
                    .password("") // JWT 场景不需要密码
                    .authorities(Collections.emptyList()) // 有角色的话从 claims 里取
                    .build();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(auth);

            // ThreadLocal 存用户，方便业务层随时取
            UserContext.setCurrentUserId(userId);

        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 已过期", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 无效", e);
        } catch (TokenException e) {
            throw new TokenException(e.getMessage());
        }

        chain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 登录/注册等公开接口不走 JWT 校验
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
    }
}
