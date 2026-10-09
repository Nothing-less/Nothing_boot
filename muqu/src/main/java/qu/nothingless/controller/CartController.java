package qu.nothingless.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import mu.nothingless.security.UserContext;
import qu.nothingless.dto.AddItemRequest;
import qu.nothingless.dto.CartView;
import qu.nothingless.entity.CartItem;
import qu.nothingless.service.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 购物车 REST 接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    // ---------- 查询 ----------

    /** 购物车列表 + 合计 */
    @GetMapping
    public CartView list(@RequestHeader(value = "X-Guest-Token", required = false) String guestToken) {
        Long userId = currentUserId();
        if (userId == null) {
            List<CartItem> items = guestToken == null ? List.of() : cartService.listGuest(guestToken);
            return CartView.of(items);
        }
        return CartView.of(cartService.list(userId));
    }

    /** 仅取汇总信息，避免渲染前拉全量 */
    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Long userId = currentUserId();
        if (userId == null) {
            return Map.of("quantity", 0, "types", 0, "total", BigDecimal.ZERO);
        }
        return Map.of(
                "quantity", cartService.totalQuantity(userId),
                "types", cartService.typeCount(userId),
                "total", cartService.total(userId));
    }

    // ---------- 写操作 ----------

    /** 加购 */
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItem add(@Valid @RequestBody AddItemRequest req) {
        Long userId = currentUserId();
        // 快照价由调用方传入（前端展示价），服务端在高并发下应以商品服务实时价为准
        CartItem item = CartItem.of(String.valueOf(req.skuId()), req.productId(), req.title(), req.image(),
                req.price(), req.quantity());
        return userId == null
                ? cartService.addGuestItem(req.guestToken(), item)
                : cartService.addItem(userId, item);
    }

    /** 改数量 */
    @PatchMapping("/items/{skuId}/quantity")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long skuId,
                                               @RequestParam @Min(1) @Max(999) int quantity) {
        Long userId = currentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.updateQuantity(userId, skuId, quantity);
        return ResponseEntity.noContent().build();
    }

    /** 勾选切换 */
    @PatchMapping("/items/{skuId}/selected")
    public ResponseEntity<Void> setSelected(@PathVariable Long skuId,
                                            @RequestParam boolean selected) {
        Long userId = currentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.setSelected(userId, skuId, selected);
        return ResponseEntity.noContent().build();
    }

    /** 全选 / 全不选 */
    @PatchMapping("/select-all")
    public ResponseEntity<Void> selectAll(@RequestParam boolean selected) {
        Long userId = currentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.selectAll(userId, selected);
        return ResponseEntity.noContent().build();
    }

    /** 删除单个 */
    @DeleteMapping("/items/{skuId}")
    public ResponseEntity<Void> remove(@PathVariable Long skuId) {
        Long userId = currentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.removeItem(userId, skuId);
        return ResponseEntity.noContent().build();
    }

    /** 清空 */
    @DeleteMapping
    public ResponseEntity<Void> clear() {
        Long userId = currentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        cartService.clear(userId);
        return ResponseEntity.noContent().build();
    }

    /** 登录后合并访客购物车 */
    @PostMapping("/merge")
    public Map<String, Integer> merge(@RequestParam String guestToken) {
        Long userId = currentUserId();
        if (userId == null) {
            return Map.of("merged", 0);
        }
        return Map.of("merged", cartService.mergeGuestCart(guestToken, userId));
    }

    private Long currentUserId() {
        return UserContext.getCurrentUserId() == null ? null : Long.valueOf(UserContext.getCurrentUserId());
    }
}

