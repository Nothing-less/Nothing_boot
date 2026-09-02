package mu.nothingless.security;

/**
 * 用户上下文（线程隔离）
 * <p>存储当前登录用户的 userId 和 tenantId，供 Service 层自动填充、日志追踪等使用</p>
 * <p>必须在请求入口（Filter/Interceptor）设置，在请求出口清理，防止内存泄漏</p>
 */
public final class UserContext {

    private UserContext() {}

    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_NAME = new ThreadLocal<>();
    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    // ==================== User ID ====================

    public static void setCurrentUserId(String userId) {
        USER_ID.set(userId);
    }

    public static String getCurrentUserId() {
        return USER_ID.get();
    }
    // ==================== User NAME ====================

    public static void setCurrentUserName(String userName) {
        USER_NAME.set(userName);
    }

    public static String getCurrentUserName() {
        return USER_NAME.get();
    }

    // ==================== Tenant ID ====================

    public static void setCurrentTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static Long getCurrentTenantId() {
        return TENANT_ID.get();
    }

    // ==================== 快捷判断 ====================

    public static boolean isAuthenticated() {
        return getCurrentUserId() != null;
    }

    // ==================== 清理（必须在请求结束时调用）====================

    public static void clear() {
        USER_ID.remove();
        TENANT_ID.remove();
    }
}