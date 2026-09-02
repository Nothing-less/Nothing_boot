package mu.nothingless.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mu.nothingless.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 更新用户请求 DTO
 */
@Data
public class UserUpdateRequest {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Size(max = 18, message = "用户名长度不能超过18")
    private String userAccount;

    @Size(max = 20, message = "用户ID长度不能超过20")
    private String userId;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱过长")
    private String myEmail;

    @Size(max = 20, message = "手机号长度不能超过20")
    private String phone;

    @Size(max = 64, message = "索引长度异常")
    private String phoneIndex;

    @Size(max = 20, message = "员工ID长度不能超过20")
    private String employeeId;

    @Size(max = 50, message = "名字长度不能超过50")
    private String firstName;

    @Size(max = 50, message = "姓氏长度不能超过50")
    private String lastName;

    @Size(max = 100, message = "全名长度不能超过100")
    private String fullName;

    private AccountStatus status;

    private LocalDateTime lockedUntil;

    private LocalDateTime passwordExpiresAt;

    private Integer failedLoginAttempts;

    private Set<String> myRoles;

    private Map<String, Object> myProfiles;

    private String myKey;

    private String myKey1;

    private String myKey2;

    private String myKey3;

    private String myKey4;

    private String myKey5;

    private String myKey6;
}