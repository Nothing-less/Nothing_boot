package mu.nothingless.handler;


import mu.nothingless.enums.AccountStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(AccountStatus.class)
public class AccountStatusTypeHandler extends BaseTypeHandler<AccountStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode()); // 写入时把 int code 写进去
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value == null) return null;
        int code = Integer.parseInt(value); // VARCHAR '0' → int 0
        for (AccountStatus status : AccountStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null; // 或者返回 AccountStatus.ACTIVE 作为默认值
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value == null) return null;
        int code = Integer.parseInt(value);
        for (AccountStatus status : AccountStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    @Override
    public AccountStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value == null) return null;
        int code = Integer.parseInt(value);
        for (AccountStatus status : AccountStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
