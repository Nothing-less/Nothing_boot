package mu.nothingless.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.dto.mapping.UserConvertor;
import mu.nothingless.dto.UserCreateRequest;
import mu.nothingless.entity.UserEntity;
import mu.nothingless.mapper.UserMapper;
import mu.nothingless.service.user.UserService;
import mu.nothingless.utils.AesGcmUtil;
import mu.nothingless.utils.HmacSha256Util;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final UserMapper userMapper;
    private final UserConvertor userConvertor;

    private final AesGcmUtil aesGcmUtil;
    private final HmacSha256Util hmacSha256Util;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public Optional<UserEntity> findByUserAccount(String userAccount) {
        List<UserEntity> user = userMapper.selectByUserAccount(userAccount);
        if (!user.isEmpty()) {
            var userEntity = user.get(0);
            log.info(userEntity.toString());
            String pwd = userEntity.getPasswordHash(); // 获取密码哈希
        }
        return user.isEmpty() ? Optional.empty() : Optional.of(user.get(0));
    }

    @Transactional
    public UserEntity register(UserCreateRequest request) {

        String username = request.getUserAccount();
        UserEntity exists = findByUserAccount(username).orElse(null);
        if (exists != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "用户名已存在");
        }
        return createUser(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity createUser(UserCreateRequest request) {

        UserEntity entity = userConvertor.toEntity(request);

        if (StringUtils.hasText(entity.getPhone())) {
            String plainPhone = entity.getPhone();
            entity.setPhoneIndex(hmacSha256Util.encryptToString(plainPhone)); // 先用明文算索引
            entity.setPhone(aesGcmUtil.encryptToString(plainPhone));           // 再覆盖为密文
        }
        entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        entity.setFailedAttempts(0);
        entity.setStatus(mu.nothingless.entity.enums.AccountStatus.ACTIVE);
        save(entity);
        log.info("用户创建成功 | userId={} | tenantId={}", entity.getUserId(), entity.getTenantId());
        return entity;
    }

    @SuppressWarnings("null")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoles(Long id, Set<String> roles) {
        LambdaUpdateWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaUpdate()
                .eq(UserEntity::getId, id)
                .set(UserEntity::getMyRoles, roles);

        return update(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUser(Long id) {
        boolean ok = removeById(id);
        if (ok) {
            log.info("用户删除成功 | id={}", id);
        }
        return ok;
    }

    @Override
    public Vector<UserEntity> getAllUser() {
        Vector<UserEntity> retUserList = new Vector<>();
        try {
            var all_user_list = list();
            // var all_user_list = userMapper.selectAll();
            if (all_user_list.isEmpty()) {
                return retUserList;
            }
            retUserList.addAll(all_user_list);
        } catch (Exception e) {
            log.error("Error happened: ", e);
        }
        return retUserList;
    }
}
