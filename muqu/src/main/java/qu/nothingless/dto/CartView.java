package qu.nothingless.dto;

import qu.nothingless.entity.CartItem;
import java.math.BigDecimal;
import java.util.List;

public record CartView(List<CartItem> items, int quantity, BigDecimal total) {
        @SuppressWarnings("null")
        public static CartView of(List<CartItem> items) {
            int qty = items.stream().mapToInt(CartItem::quantity).sum();
            BigDecimal total = items.stream()
                    .filter(CartItem::selected)
                    .map(CartItem::subtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return new CartView(items, qty, total);
        }
}