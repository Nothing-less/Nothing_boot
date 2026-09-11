package mu.nothingless.service.user;

import mu.nothingless.dto.UserCreateRequest;
import mu.nothingless.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import com.baomidou.mybatisplus.spring.service.IService;

public interface UserService extends IService<UserEntity> {

    Optional<UserEntity> findByUserId(String userId);

    UserEntity createUser(UserCreateRequest request);

    boolean updateRoles(Long id, Set<String> roles);

    boolean removeUser(Long id);

    List<UserEntity> getAllUser();

    Optional<UserEntity> findByUserAccount(String userAccount);
}
