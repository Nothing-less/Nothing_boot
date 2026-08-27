package mu.nothingless.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mu.nothingless.enums.AccountStatus;
import mu.nothingless.handler.AesTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 系统用户实体
 * <p>仅负责与数据库表的 ORM 映射，不包含任何业务校验逻辑</p>
 */
@Getter
@Setter
@ToString(exclude = {"passwordHash", "phone"})
@TableName(value = "sys_user", autoResultMap = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 20161712L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @TableField("tab_id")
    private Long id;

    // ==================== 核心字段 ====================
    @TableField("my_user_name")
    private String username;

    @TableField("my_user_id")
    private String userId;

    @TableField("my_email")
    private String myEmail;

    /** 手机号：AES-GCM 加密存储，等值查询需配合 phoneIndex 盲索引列 */
    @TableField(value = "my_phone", typeHandler = AesTypeHandler.class)
    private String phone;

    /** 手机号盲索引（HMAC-SHA256），用于唯一校验与等值查询 */
    @TableField("my_phone_index")
    private String phoneIndex;

    @TableField("my_employee_id")
    private String employeeId;

    // ==================== 姓名信息 ====================
    @TableField("my_first_name")
    private String firstName;

    @TableField("my_last_name")
    private String lastName;

    @TableField("my_full_name")
    private String fullName;

    // ==================== 安全字段 ====================
    @TableField(value = "password_hash", insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NEVER, select = false)
    private String passwordHash;

    @TableField("my_account_status")
    private AccountStatus status;

    @TableField("time_locked_until")
    private LocalDateTime lockedUntil;

    @TableField("time_password_expires_at")
    private LocalDateTime passwordExpiresAt;

    @TableField("failed_attempts")
    private Integer failedAttempts;

    // 角色集合：JSON 字段自动序列化
    @TableField(value = "my_roles", typeHandler = JacksonTypeHandler.class)
    private Set<String> myRoles;

    @TableField(value = "my_profiles", typeHandler = JacksonTypeHandler.class)
    private Set<String> myProfiles;

    // ==================== 乐观锁 ====================
    @Version
    @TableField("my_version")
    private Integer myVersion;

    // ==================== 逻辑删除 ====================
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "bool_deleted", fill = FieldFill.INSERT)
    private Integer deleted;

    // ==================== 自动填充字段（审计） ====================
    @TableField(value = "time_created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "time_updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(value = "who_created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "who_updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    // ==================== 租户字段 ====================
    @TableField(value = "my_tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    // ==================== 外键(自己关联) ====================
    @TableField("my_key")
    private String myKey;

    // ==================== 备用键 ====================
    @TableField("key_01")
    private String myKey1;

    @TableField("key_02")
    private String myKey2;

    @TableField("key_03")
    private String myKey3;

    @TableField("key_04")
    private String myKey4;

    @TableField("key_05")
    private String myKey5;

    @TableField("key_06")
    private String myKey6;

    public UserEntity() {
        this.myVersion = 0;
        this.failedAttempts = 0;
        this.deleted = 0;
        this.myRoles = new HashSet<>();
        this.myProfiles = new HashSet<>();
    }

    public Set<String> getRoles() {
        if (myRoles == null) {
            myRoles = new HashSet<>();
        }
        return myRoles;
    }

    public Set<String> getProfiles() {
        if (myProfiles == null) {
            myProfiles = new HashSet<>();
        }
        return myProfiles;
    }
}