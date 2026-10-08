package qu.nothingless.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import qu.nothingless.service.ProductService;

@Slf4j
@RestController
@RequestMapping("/api/test2/products")
@RequiredArgsConstructor
@Validated
public class Test2Controller {
    private final ProductService productService;

    @PostMapping("/create")
    public void createProduct() {
        productService.testCreate();
    }

    @GetMapping ("/all_1")
    public List<qu.nothingless.entity.ProductEntity> getAllProducts() {
        return productService.test1Get();
    }

    @GetMapping ("/all_2")
    public List<qu.nothingless.entity.ProductEntity> getAllProducts2() {
        return productService.test2Get();
    }
}
