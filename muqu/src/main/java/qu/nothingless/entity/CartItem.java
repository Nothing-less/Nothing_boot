package qu.nothingless.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record CartItem(
        String skuId,
        Long productId,
        String title,
        String image,
        BigDecimal price,      // 加购时的价格快照
        Integer quantity,
        Boolean selected,
        Long addedAt           // 加购时间戳，用于排序
) {
    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 200;

    public CartItem {
        if (skuId == null || skuId.isBlank()) {
            throw new IllegalArgumentException("skuId must not be blank");
        }
        Objects.requireNonNull(price, "price must not be null");
        if (price.signum() < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        Objects.requireNonNull(quantity, "quantity must not be null");
        Objects.requireNonNull(selected, "selected must not be null");
        Objects.requireNonNull(addedAt, "addedAt must not be null");
        quantity = Math.min(Math.max(quantity, MIN_QUANTITY), MAX_QUANTITY);
        if (addedAt <= 0) {
            addedAt = System.currentTimeMillis();
        }
    }

    public static CartItem of(ProductEntity product) {
        return new CartItem(
                product.getProductSku(),
                product.getProductId(),
                product.getProductName(),
                product.getProductImage(),
                product.getProductPrice(),
                MIN_QUANTITY,
                true,
                System.currentTimeMillis()
        );
    }

    /** 新建条目：默认选中，时间取当前 */
    public static CartItem of(String skuId, Long productId, String title, String image,
                              BigDecimal price, int quantity) {
        return new CartItem(skuId, productId, title, image, price, quantity, true,
                System.currentTimeMillis());
    }

    /** 直接覆盖数量（自动 clamp 到 [MIN, MAX]） */
    public CartItem withQuantity(int newQuantity) {
        return new CartItem(skuId, productId, title, image, price, newQuantity, selected, addedAt);
    }

    /** 累加数量（加购时用，自动 clamp） */
    public CartItem plusQuantity(int delta) {
        long summedQuantity = (long) quantity + delta;
        int boundedQuantity = (int) Math.min(Math.max(summedQuantity, MIN_QUANTITY), MAX_QUANTITY);
        return withQuantity(boundedQuantity);
    }

    public CartItem withSelected(boolean s) {
        return new CartItem(skuId, productId, title, image, price, quantity, s, addedAt);
    }

    /** 小计 = 快照价 × 数量 */
    public BigDecimal subtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "skuId=" + skuId +
                ", productId=" + productId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", selected=" + selected +
                ", addedAt=" + addedAt +
                '}';
    }


}
