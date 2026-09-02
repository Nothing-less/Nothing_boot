package mu.nothingless.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import mu.nothingless.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 用户响应 DTO
 */
@Data
public class UserResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String userAccount;
    private String userId;
    private String myEmail;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String fullName;
    private AccountStatus status;
    private LocalDateTime lockedUntil;
    private LocalDateTime passwordExpiresAt;
    private Integer failedLoginAttempts;
    private Set<String> myRoles;
    private Map<String, Object> myProfiles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long tenantId;

    // 不包含 passwordHash、phone、phoneIndex 等敏感字段
}