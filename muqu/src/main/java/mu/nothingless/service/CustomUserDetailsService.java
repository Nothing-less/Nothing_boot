package mu.nothingless.service;

import lombok.RequiredArgsConstructor;
import mu.nothingless.utils.BcryptUtil;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final BcryptUtil bcrypt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 示例：这里改成你从数据库查用户的逻辑
        // UserEntity user = userMapper.findByUsername(username);
        // if (user == null) throw new UsernameNotFoundException("用户不存在");
        
        // 临时写死用于测试，后续替换为数据库查询
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        return User.builder()
                .username("admin")
                .password(bcrypt.encode("123456")) // BCrypt
                .roles("ADMIN")
                .build();
    }
}
