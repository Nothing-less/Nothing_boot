package mu.nothingless.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;

import mu.nothingless.handler.AesTypeHandler;
import mu.nothingless.utils.AesGcmUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {


    @Bean
    public ConfigurationCustomizer typeHandlerConfigurationCustomizer(AesGcmUtil aesGcmUtil) {
        return configuration -> {
            // 注册带 Spring Bean 的 TypeHandler
            configuration.getTypeHandlerRegistry().register(
                String.class, 
                JdbcType.VARCHAR, 
                new AesTypeHandler(aesGcmUtil)
            );
        };
    }


    /**
     * 插件配置：分页 + 乐观锁 + 多租户 + 动态表名 + 防全表更新删除
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 1. 多租户插件（行级数据隔离）
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler()));

        // 2. 分页插件（必须放在多租户之后）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));

        // 3. 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 4. 防全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 5. 动态表名插件（分表场景）
        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor(dynamicTableNameHandler()));

        return interceptor;
    }

    private TenantLineHandler tenantLineHandler() {
        return new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // 从上下文获取当前租户ID
                // return new LongValue(TenantContextHolder.getTenantId());
                return new LongValue(1L);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean ignoreTable(String tableName) {
                // 忽略不需要租户隔离的表
                return "sys_config".equals(tableName) || "sys_dict".equals(tableName);
            }
        };
    }

    private TableNameHandler dynamicTableNameHandler() {
        return (sql, tableName) -> {
            // 分表逻辑：user_202601, user_202602...
            // return tableName + "_" + getCurrentMonth();
            return tableName;
        };
    }
}