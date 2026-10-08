package qu.nothingless.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import mu.nothingless.entity.enums.BaseEnum;

@Getter
public enum ProductStatusEnum implements BaseEnum<Integer> {

    // 65280 #00FF00
    ACTIVE(0x00FF00, "ACTIVE"),

    // 16711680 #FF0000
    INACTIVE(0xFF0000, "INACTIVE"),

    // 255 #0000FF
    LOCKED(0x0000FF, "LOCKED"),

    // 16776960 #FFFF00
    EXPIRED(0xFFFF00, "EXPIRED"),


    // 0 #000000
    DELETED(0x000000, "DELETED");

    @SuppressWarnings("null")
	private static final Map<Integer, ProductStatusEnum> CODE_MAP =
            Arrays.stream(values())
                  .collect(Collectors.collectingAndThen(
                          Collectors.toMap(ProductStatusEnum::getCode, Function.identity()),
                          Collections::unmodifiableMap));

    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    public static ProductStatusEnum of(Integer code) {
        if (code == null) {
            return null;
        }
        ProductStatusEnum status = CODE_MAP.get(code);
        if (status == null) {
            throw new IllegalArgumentException("未知的商品状态码: " + code);
        }
        return status;
    }

}
