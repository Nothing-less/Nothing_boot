package qu.nothingless.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AddItemRequest(
            @NotNull Long skuId,
            Long productId,
            String title,
            String image,
            @NotNull BigDecimal price,
            @Min(1) @Max(999) int quantity,
            String guestToken
    ) {}
