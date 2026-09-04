package mu.nothingless.controller;

import java.util.Vector;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.dto.request.LoginRequest;
import mu.nothingless.dto.request.UserCreateRequest;
import mu.nothingless.entity.UserEntity;
import mu.nothingless.service.user.UserService;


@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Validated
public class TestController {

    private final UserService userService;
    
    @GetMapping("/getAll")
    public Vector getAllUser() {
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
    
}
