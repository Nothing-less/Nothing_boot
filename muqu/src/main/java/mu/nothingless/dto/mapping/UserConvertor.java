package mu.nothingless.dto.mapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import mu.nothingless.dto.request.UserCreateRequest;
import mu.nothingless.dto.request.UserUpdateRequest;
import mu.nothingless.dto.response.UserResponse;
import mu.nothingless.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Mapper(
    componentModel = "spring",
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE  // 忽略未显式映射的字段
)
public interface UserConvertor {

    // ========== DTO → Entity（创建） ==========
    
    @Mapping(source = "password", target = "passwordHash")
    @Mapping(target = "id", ignore = true)                    // 创建时 ID 由数据库生成
    @Mapping(target = "createdAt", ignore = true)             // 自动填充
    @Mapping(target = "updatedAt", ignore = true)             // 自动填充
    @Mapping(target = "createdBy", ignore = true)              // 自动填充
    @Mapping(target = "updatedBy", ignore = true)            // 自动填充
    @Mapping(target = "deleted", ignore = true)               // 默认 0
    @Mapping(target = "myVersion", ignore = true)            // 默认 0
    @Mapping(target = "failedAttempts", constant = "0")      // 新用户登录失败次数为 0
    @Mapping(target = "myRoles", expression = "java(copySet(request.getMyRoles()))")
    @Mapping(target = "myProfiles", expression = "java(copySet(request.getMyProfiles()))")
    UserEntity toEntity(UserCreateRequest request);

    // ========== DTO → Entity（更新，忽略 null） ==========
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "failedLoginAttempts", target = "failedAttempts")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)          // 更新 DTO 不带密码，单独处理
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "myVersion", ignore = true)
    @Mapping(target = "tenantId", ignore = true)              // 租户不允许更新
    @Mapping(target = "myRoles", expression = "java(copySet(request.getMyRoles()))")
    @Mapping(target = "myProfiles", expression = "java(copySet(request.getMyProfiles()))")
    void updateEntityFromDto(UserUpdateRequest request, @MappingTarget UserEntity entity);

    // ========== Entity → Response ==========
    
    @Mapping(source = "failedAttempts", target = "failedLoginAttempts")
    UserResponse toResponse(UserEntity entity);

    // ========== 深拷贝 Set（防止外部修改影响实体） ==========
    
    default Set<String> copySet(Set<String> source) {
        return source == null ? new HashSet<>() : new HashSet<>(source);
    }
}