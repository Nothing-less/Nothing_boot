package mu.nothingless.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import mu.nothingless.enums.AccountStatus;

/**
 * 用户查询请求 DTO
 */
@Data
public class UserQueryRequest {

    @Size(max = 18, message = "用户名长度不能超过18")
    private String userAccount;

    @Size(max = 20, message = "用户ID长度不能超过20")
    private String userId;

    @Size(max = 100, message = "邮箱过长")
    private String myEmail;

    @Size(max = 50, message = "名字长度不能超过50")
    private String firstName;

    @Size(max = 50, message = "姓氏长度不能超过50")
    private String lastName;

    private AccountStatus status;

    private String employeeId;

    private Long tenantId;

    // 分页参数（也可继承公共 PageRequest）
    private Long pageNum = 1L;
    private Long pageSize = 10L;
}