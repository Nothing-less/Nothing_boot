package mu.nothingless.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.dto.request.LoginRequest;
import mu.nothingless.dto.response.AccessTokenResponse;
import mu.nothingless.dto.response.TokenPairResponse;
import mu.nothingless.dto.response.UserInfo;
import mu.nothingless.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenPairResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refresh(
            @RequestHeader("X-Refresh-Token") @NotBlank(message = "刷新令牌不能为空") String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserInfo me(Authentication authentication) {
        // 直接注入 Authentication
        return new UserInfo(authentication.getName());
    }
}