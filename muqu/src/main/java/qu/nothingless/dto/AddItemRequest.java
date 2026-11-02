package qu.nothingless.dto;

import java.math.BigDecimal;

public record AddItemRequest(
        Long skuId,
        Long productId,
        String title,
        String image,
        BigDecimal price,
        int quantity,
        String guestToken
) {
}
