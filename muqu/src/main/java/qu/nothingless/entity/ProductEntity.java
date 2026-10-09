package qu.nothingless.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import qu.nothingless.entity.enums.ProductStatusEnum;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data 
@TableName("t_product")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 10086L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "product_id", type = IdType.ASSIGN_ID)
    private Long productId;

    @TableField("product_name")
    private String productName;

    @TableField("product_description")
    private String productDescription;

    @TableField("product_price")
    private BigDecimal productPrice;

    @TableField("product_image")
    private String productImage;

    @TableField("product_category")
    private String productCategory;

    @TableField("product_brand")
    private String productBrand;

    @TableField("product_stock")
    private Integer productStock;

    @TableField("product_status")
    private ProductStatusEnum productStatus;

    @TableField(value = "product_created_at", fill = FieldFill.INSERT)
    private LocalDateTime productCreatedAt;

    @TableField(value = "product_updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime productUpdatedAt;

    @TableField("product_sku")
    private String productSku;

    // ==================== 逻辑删除 ====================
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Integer deleted;

    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;
}