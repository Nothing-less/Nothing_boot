package mu.nothingless.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.dto.mapping.UserConvertor;
import mu.nothingless.dto.request.UserCreateRequest;
import mu.nothingless.entity.UserEntity;
import mu.nothingless.mapper.UserMapper;
import mu.nothingless.service.user.UserService;
import mu.nothingless.utils.HmacSha256Util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import java.util.Optional;
import java.util.Set;
import java.util.Vector;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final UserMapper userMapper;
    private final UserConvertor userConvertor;
    private final HmacSha256Util hmacSha256Util;


    @Override
    public Optional<UserEntity> findByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity createUser(UserCreateRequest request) {

        UserEntity entity = userConvertor.toEntity(request);

        if (StringUtils.hasText(entity.getPhone())) {
            entity.setPhoneIndex(generatePhoneIndex(entity.getPhone()));
        }
        entity.setFailedAttempts(0);
        entity.setStatus(mu.nothingless.enums.AccountStatus.ACTIVE);
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

    private String generatePhoneIndex(String phone) {
        return hmacSha256Util.generatePhoneIndex(phone);
    }

    @Override
    public Vector<UserEntity> getAllUser() {
        Vector<UserEntity> retUserList = new Vector<>();
        try {
            var all_user_list = list();
            if(all_user_list.isEmpty()){
                return retUserList;
            }
            retUserList.addAll(all_user_list);
        } catch (Exception e) {
            log.error("Error happened: ", e);
        }
        return retUserList;
    }
}
