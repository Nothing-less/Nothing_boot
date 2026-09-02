package mu.nothingless.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PostgreSQL jsonb 专用类型处理器
 * 继承 JacksonTypeHandler 保留查询时的反序列化能力，
 * 只重写插入逻辑，使用 PGobject 让 PostgreSQL 识别为 jsonb 类型。
 */
@MappedTypes({Object.class})
public class PgJsonbTypeHandler extends JacksonTypeHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public PgJsonbTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("jsonb");
        try {
            pgObject.setValue(MAPPER.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to convert object to jsonb", e);
        }
        ps.setObject(i, pgObject);
    }
}