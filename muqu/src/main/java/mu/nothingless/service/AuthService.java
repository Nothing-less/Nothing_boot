package mu.nothingless.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.dto.request.LoginRequest;
import mu.nothingless.dto.response.AccessTokenResponse;
import mu.nothingless.dto.response.TokenPairResponse;
import mu.nothingless.exception.BusinessException;
import mu.nothingless.security.jwt.JwtProperties;
import mu.nothingless.security.jwt.JwtUtil;
import mu.nothingless.utils.RetResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public TokenPairResponse login(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String userId = authentication.getName();
        String username = request.username();

        var token_pair = jwtUtil.generateToken(userId, username, null);
        String accessToken = token_pair.accessToken();
        String refreshToken = token_pair.refreshToken();

        log.info("用户 [{}] 登录成功", username);

        return new TokenPairResponse(
                accessToken,
                refreshToken,
                jwtProperties.getAccessTtl().getSeconds());
    }

    public AccessTokenResponse refresh(String refreshToken) {
        try {
            Claims claims = jwtUtil.parseToken(refreshToken);
            if (!JwtUtil.isRefreshToken(claims)) {
                throw new BusinessException(RetResult.UNAUTHORIZED, "非法 Token 类型");
            }
            String userId = JwtUtil.getUserId(claims);

            // 从 refresh token claims 取 username
            String username = claims.get("username", String.class);
            if (username == null || username.isBlank()) {
                log.warn("Refresh Token 中未找到 username，userId={}", userId);
                username = userId; // 或者查数据库，或者抛异常
            }

            String newAccessToken = jwtUtil.generateToken(userId, username, null).accessToken();

            log.debug("用户 [{}] 刷新访问令牌成功", userId);

            return new AccessTokenResponse(
                    newAccessToken,
                    jwtProperties.getAccessTtl().getSeconds());
        } catch (Exception e) {
            return null;
        }
    }
}
