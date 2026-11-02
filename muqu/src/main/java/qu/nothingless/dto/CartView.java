package qu.nothingless.dto;

import java.math.BigDecimal;
import java.util.List;

import qu.nothingless.entity.CartItem;
import qu.nothingless.vo.CartItemVO;

public record CartView(List<CartItemVO> items, int quantity, BigDecimal total) {
    @SuppressWarnings("null")
    public static CartView of(List<CartItem> items) {
        int qty = items.stream().mapToInt(CartItem::quantity).sum();
        BigDecimal total = items.stream()
                .filter(CartItem::selected)
                .map(CartItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartView(items.stream().map(CartItemVO::from).toList(), qty, total);
    }
}