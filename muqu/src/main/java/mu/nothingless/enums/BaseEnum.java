package mu.nothingless.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * MP 支持的枚举接口：IEnum<T>
 * 数据库存 code，Java 用枚举，JSON 序列化也返回 desc
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T> {
    
    @JsonValue  // Jackson 序列化时返回此方法值
    String getDesc();
}
