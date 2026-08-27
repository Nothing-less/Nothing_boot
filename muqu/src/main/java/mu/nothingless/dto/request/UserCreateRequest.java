package mu.nothingless.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mu.nothingless.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 创建用户请求 DTO
 */
@Data
public class UserCreateRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 18, message = "用户名长度不能超过18")
    private String username;

    @Size(max = 20, message = "用户ID长度不能超过20")
    private String userId;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱过长")
    private String myEmail;

    @NotBlank(message = "手机号不能为空")
    @Size(max = 20, message = "手机号长度不能超过20")
    private String phone;

    @NotBlank(message = "手机号索引不能为空")
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

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 128, message = "密码长度必须在6-128位之间")
    private String password;

    private AccountStatus status;

    private LocalDateTime lockedUntil;

    private LocalDateTime passwordExpiresAt;

    private Set<String> myRoles;

    private Set<String> myProfiles;

    private String myKey;

    private String myKey1;

    private String myKey2;

    private String myKey3;

    private String myKey4;

    private String myKey5;

    private String myKey6;
}