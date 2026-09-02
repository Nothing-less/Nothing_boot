package mu.nothingless.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum AccountStatus implements BaseEnum<Integer> {

    // 65280 #00FF00
    ACTIVE(0x00FF00, "ACTIVE"),

    // 16711680 #FF0000
    INACTIVE(0xFF0000, "INACTIVE"),

    // 255 #0000FF
    LOCKED(0x0000FF, "LOCKED"),

    // 16776960 #FFFF00
    EXPIRED(0xFFFF00, "EXPIRED"),

    // 8388863 #8000FF
    PENDING(0x8000FF, "PENDING"),

    // 0 #000000
    DELETED(0x000000, "DELETED");

    @SuppressWarnings("null")
    private static final Map<Integer, AccountStatus> CODE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(AccountStatus::getCode, Function.identity()));

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

    AccountStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    public static AccountStatus of(Integer code) {
        return CODE_MAP.get(code);
    }

    // public String describeStatus(AccountStatus status) {
    //     return switch (status) {
    //         case ACTIVE -> "正常";
    //         case LOCKED -> "已锁定至 ";
    //         case EXPIRED -> "密码过期";
    //         case null -> "未知";
    //         case DELETED -> throw new UnsupportedOperationException("Unimplemented case: " + status);
    //         case INACTIVE -> throw new UnsupportedOperationException("Unimplemented case: " + status);
    //         case PENDING -> throw new UnsupportedOperationException("Unimplemented case: " + status);
    //         default -> throw new IllegalArgumentException("Unexpected value: " + status);
    //     };
    // }
}