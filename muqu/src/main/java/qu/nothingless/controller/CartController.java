package qu.nothingless.controller;

import lombok.RequiredArgsConstructor;
import qu.nothingless.dto.AddItemRequest;
import qu.nothingless.dto.CartView;
import qu.nothingless.service.CartService;
import qu.nothingless.vo.CartItemVO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return cartService.getCurrentCart(guestToken);
    }

    /** 仅取汇总信息，避免渲染前拉全量 */
    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return cartService.getCurrentSummary();
    }

    // ---------- 写操作 ----------

    /** 加购 */
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemVO add(@RequestBody AddItemRequest req) {
        return cartService.addCurrentItem(req);
    }

    /** 改数量 */
    @PatchMapping("/items/{skuId}/quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuantity(@PathVariable Long skuId, @RequestParam int quantity) {
        cartService.updateCurrentQuantity(skuId, quantity);
    }

    /** 勾选切换 */
    @PatchMapping("/items/{skuId}/selected")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSelected(@PathVariable Long skuId, @RequestParam boolean selected) {
        cartService.setCurrentSelected(skuId, selected);
    }

    /** 全选 / 全不选 */
    @PatchMapping("/select-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void selectAll(@RequestParam boolean selected) {
        cartService.selectAllForCurrentUser(selected);
    }

    /** 删除单个 */
    @DeleteMapping("/items/{skuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long skuId) {
        cartService.removeCurrentItem(skuId);
    }

    /** 清空 */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear() {
        cartService.clearCurrentCart();
    }

    /** 登录后合并访客购物车 */
    @PostMapping("/merge")
    public Map<String, Integer> merge(@RequestParam String guestToken) {
        return cartService.mergeCurrentGuestCart(guestToken);
    }
}
