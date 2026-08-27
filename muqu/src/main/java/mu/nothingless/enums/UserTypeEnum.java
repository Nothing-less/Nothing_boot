package mu.nothingless.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserTypeEnum implements BaseEnum<Integer> {

    ADMIN(1, "Administrator"),
    USER(2, "Player"),
    VIP(3, "VIP");

    @EnumValue
    private final Integer code;
    private final String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    
}