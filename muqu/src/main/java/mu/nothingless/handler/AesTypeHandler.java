package mu.nothingless.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mu.nothingless.config.SpringContextHolder;
import mu.nothingless.utils.AesGcmUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段级 AES 加密 TypeHandler
 * 入库加密，出库解密，对业务层透明
 */
@Slf4j
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
@RequiredArgsConstructor
public class AesTypeHandler extends BaseTypeHandler<String> {

    /** 解密失败时的占位符 */
    private static final String DECRYPT_FAILED_PLACEHOLDER = "***DECRYPT_FAILED***";

    private final AesGcmUtil aesUtil;

    /**
     * 无参构造器：通过 Spring 上下文获取 AesGcmUtil
     * 若 Spring 上下文未就绪（极早期初始化），将抛出 IllegalStateException 快速失败。
     */
    public AesTypeHandler() {
        this.aesUtil = SpringContextHolder.getBean(AesGcmUtil.class);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, encrypt(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return decrypts(value, columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return decrypts(value, String.valueOf(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return decrypts(value, String.valueOf(columnIndex));
    }

    public String decrypt(String cipherText) throws SQLException {
        try {
            return aesUtil.decryptToString(cipherText);
        } catch (Exception e) {
            throw new SQLException("AES decryptString failed", e);
        }
    }

    public String encrypt(String plainText) throws SQLException {
        try {
            return aesUtil.encryptToString(plainText);
        } catch (Exception e) {
            throw new SQLException("AES encryptString failed", e);
        }
    }

    /**
     * 安全解密：单条失败不阻断整体查询
     */
    private String decrypts(String cipherText, String columnRef) {
        if (cipherText == null) {
            return null;
        }
        try {
            return aesUtil.decryptToString(cipherText);
        } catch (Exception e) {
            log.error("AES decrypt failed, column: {}, cipherText prefix: {}",
                    columnRef,
                    cipherText.length() > 8 ? cipherText.substring(0, 8) + "..." : cipherText,
                    e);
            return DECRYPT_FAILED_PLACEHOLDER;
        }
    }

    /**
     * return true if descrypt failed
     */
    public static Boolean isFailedDescrypt(String str) {
        return !DECRYPT_FAILED_PLACEHOLDER.equals(str);
    }

}