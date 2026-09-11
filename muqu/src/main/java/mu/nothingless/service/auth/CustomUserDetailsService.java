package mu.nothingless.service.auth;

import lombok.RequiredArgsConstructor;
import mu.nothingless.entity.UserEntity;
import mu.nothingless.entity.enums.AccountStatus;
import mu.nothingless.service.user.UserService;
import mu.nothingless.utils.HmacSha256Util;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final HmacSha256Util hmacSha256Util;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        // 通过账号查用户
        UserEntity user = userService.findByUserAccount(userAccount)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + userAccount));

        // AccountStatus 校验
        if (user.getStatus() != AccountStatus.ACTIVE) {
            throw new LockedException("账号已被锁定");
        }

        return User.builder()
                .username(user.getUserId()) // 用 userId 作为 principal
                .password(user.getPasswordHash()) // 数据库存的 SHA256 密文
                .accountLocked(user.getStatus() != AccountStatus.ACTIVE)
                .authorities(user.getMyRoles() != null
                        ? user.getMyRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                        : java.util.Collections.emptyList())
                .build();
    }

    public UserDetails _loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        // UserEntity user = userMapper.findByUsername(userAccount);
        // if (user == null) throw new UsernameNotFoundException("用户不存在");

        // 临时写死用于测试，后续替换为数据库查询
        if (!"admin".equals(userAccount)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return User.builder()
                .username("admin")
                .password(hmacSha256Util.encryptToString("123456")) // HMAC-SHA256
                .roles("ADMIN")
                .build();
    }
}
