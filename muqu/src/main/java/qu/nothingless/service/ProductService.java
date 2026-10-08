package qu.nothingless.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import qu.nothingless.entity.ProductEntity;
import qu.nothingless.mapper.ProductMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService extends ServiceImpl<ProductMapper, ProductEntity> {

    private final ProductMapper productMapper;

    public void testCreate() {
        ProductEntity entity = new ProductEntity();
        entity.setProductName("Test Product");
        entity.setProductDescription("This is a test product.");
        entity.setProductPrice(new java.math.BigDecimal("19.99"));
        entity.setProductImage("test_product.jpg");
        entity.setProductCategory("Test Category");
        entity.setProductBrand("Test Brand");
        entity.setProductStock(100);
        entity.setProductSku("null SKU");
        log.info("Created ProductEntity: {}", entity);

        var ret = productMapper.insert(entity);
        log.info("Insert operation returned: {}", ret);
        log.info("Inserted ProductEntity with ID: {}", entity.getProductId());
    }
    public List<ProductEntity> test1Get(){
        var ret = list();
        return ret;
    }
    public List<ProductEntity> test2Get() {
        var ret = productMapper.selectAllProducts();
        log.info("Retrieved Products: {}", ret);
        return ret;
    }
}