package mu.nothingless.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.security.UserContext;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;


@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("自动填充 INSERT 字段 | target={}", metaObject.getOriginalObject().getClass().getSimpleName());
        LocalDateTime now = LocalDateTime.now();

        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "failedAttempts", Integer.class, 0);
        this.strictInsertFill(metaObject, "myVersion", Integer.class, 0);

        String operator = getCurrentUsername();
        this.strictInsertFill(metaObject, "createdBy", String.class, operator);
        this.strictInsertFill(metaObject, "updatedBy", String.class, operator);

        Long tenantId = getCurrentTenantId();
        this.strictInsertFill(metaObject, "tenantId", Long.class, tenantId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("自动填充 UPDATE 字段 | target={}", metaObject.getOriginalObject().getClass().getSimpleName());
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updatedBy", String.class, getCurrentUsername());
    }

    private String getCurrentUsername() {
        // 先从业务上下文取
        String userName = UserContext.getCurrentUserName();
        if (userName != null && !userName.isEmpty()) {
            return userName;
        }

        // 再从 Spring Security 取
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "system";
        }

        // 排除匿名用户
        if ("anonymousUser".equals(authentication.getName())) {
            return "system";
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return principal.toString();
    }

    private String getCurrentUserId() {
        // 从 SecurityContextHolder 或 ThreadLocal 获取
        // return SecurityUtils.getUserId();

        String currentUserId = "";
        currentUserId = UserContext.getCurrentUserId();
        if (!"".equals(currentUserId)) {
            return currentUserId;
        }
        return "NULL";
    }

    private Long getCurrentTenantId() {
        // 从请求上下文或用户对象获取
        // return TenantContextHolder.getTenantId();

        Long currentTenantId = -1L;

        var tenantId = UserContext.getCurrentTenantId();
        if(tenantId != null) {
            currentTenantId = tenantId;
        }

        if (currentTenantId != -1L) {
            return currentTenantId;
        }
        return 1L;

    }
}