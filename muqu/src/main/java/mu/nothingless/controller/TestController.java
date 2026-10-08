package mu.nothingless.controller;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.config.SpringContextHolder;
import mu.nothingless.dto.LoginRequest;
import mu.nothingless.dto.UserCreateRequest;
import mu.nothingless.entity.UserEntity;
import mu.nothingless.service.user.UserService;
import mu.nothingless.utils.AesGcmUtil;


@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Validated
public class TestController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/getAll")
    public List<?> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/login")
    public UserEntity doLogin(@RequestBody @Valid LoginRequest request) {
        String userAccount = request.userAccount();
        String password = request.password();
        log.info("Login attempt for userAccount: {}, password: {}", userAccount, password);
        return userService.findByUserAccount(userAccount).orElse(null);
    }

    @PostMapping("/register")
    public UserEntity createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/debug")
    public String debug(@RequestBody @Valid LoginRequest request) {
        UserDetails u = userDetailsService.loadUserByUsername(request.userAccount());
        String db = u.getPassword();
        return String.join("\n",
                "encoder   = " + passwordEncoder.getClass().getName(),
                "dbValue   = " + db,
                "dbLength  = " + (db == null ? -1 : db.length()), // bcrypt 应为 60
                "matches   = " + passwordEncoder.matches(request.password(), db),
                "reEncode  = " + passwordEncoder.encode(request.password()) // 对比下格式前缀
        );
    }

	@PostConstruct
    public void logKeyFingerprint() {
        AesGcmUtil u = SpringContextHolder.getBean(AesGcmUtil.class);
        log.info("Test decrypt 3: "+u.decryptToString("v1:Z9NR848xdk+aOd8W:h5Lm3SUoSzhn47WEK7KJGj6CNpmDg4Dlk9pK"));
    }
}
