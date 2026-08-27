package mu.nothingless.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("开始插入填充...");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        
        // 从 SecurityContext 获取当前用户ID
        Long userId = getCurrentUserId();
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
        
        // 多租户ID注入
        Long tenantId = getCurrentTenantId();
        this.strictInsertFill(metaObject, "tenantId", Long.class, tenantId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始更新填充...");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateBy", Long.class, getCurrentUserId());
    }

    private Long getCurrentUserId() {
        // 从 SecurityContextHolder 或 ThreadLocal 获取
        // return SecurityUtils.getUserId();
        return 1L; // 占位
    }

    private Long getCurrentTenantId() {
        // 从请求上下文或用户对象获取
        // return TenantContextHolder.getTenantId();
        return 1L; // 占位
    }
}