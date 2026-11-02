package qu.nothingless.vo;

import java.math.BigDecimal;

import qu.nothingless.entity.CartItem;

public record CartItemVO(
        String skuId,
        Long productId,
        String title,
        String image,
        BigDecimal price,
        Integer quantity,
        Boolean selected,
        Long addedAt,
        BigDecimal subtotal
) {
    public static CartItemVO from(CartItem item) {
        return new CartItemVO(
                item.skuId(),
                item.productId(),
                item.title(),
                item.image(),
                item.price(),
                item.quantity(),
                item.selected(),
                item.addedAt(),
                item.subtotal());
    }
}
