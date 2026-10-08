package mu.nothingless.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.advice.RetResult;
import mu.nothingless.dto.LoginRequest;
import mu.nothingless.exceptions.BusinessException;
import mu.nothingless.security.jwt.JwtProperties;
import mu.nothingless.security.jwt.JwtUtil;
import mu.nothingless.vo.AccessTokenResponse;
import mu.nothingless.vo.TokenPairResponse;

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
                new UsernamePasswordAuthenticationToken(request.userAccount(), request.password()));

        String userId = authentication.getName();
        String userAccount = request.userAccount();

        var token_pair = jwtUtil.generateToken(userId, userAccount, null);
        String accessToken = token_pair.accessToken();
        String refreshToken = token_pair.refreshToken();

        log.info("用户 [{}] 登录成功", userAccount);
        // 登录成功后存入 Redis，设置过期时间


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

            // 从 refresh token claims 取 userAccount
            String userAccount = claims.get("userAccount", String.class);
            if (userAccount == null || userAccount.isBlank()) {
                log.warn("Refresh Token 中未找到 userAccount，userId={}", userId);
                userAccount = userId; // 或者查数据库，或者抛异常
            }

            String newAccessToken = jwtUtil.generateToken(userId, userAccount, null).accessToken();

            log.debug("用户 [{}] 刷新访问令牌成功", userId);

            return new AccessTokenResponse(
                    newAccessToken,
                    jwtProperties.getAccessTtl().getSeconds());
        } catch (Exception e) {
            return null;
        }
    }
}
