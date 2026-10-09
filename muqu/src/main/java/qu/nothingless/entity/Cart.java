package qu.nothingless.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data 
@TableName("t_cart")
public class Cart implements Serializable{
    private static final long serialVersionUID = 10086L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "cart_id", type = IdType.ASSIGN_ID)
    private Long cartId;

    @TableField("cart_owner_id")
    private String cartOwnerId;

    private final Map<Long, CartItem> items = new ConcurrentHashMap<>();

    private String productSku;

    // ==================== 逻辑删除 ====================
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer deleted;

    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;
}
