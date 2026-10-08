package qu.nothingless.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import qu.nothingless.entity.ProductEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper 
public interface ProductMapper extends BaseMapper<ProductEntity> {

    @Select ("SELECT * FROM t_product")
    List<ProductEntity> selectAllProducts();

    @Select ("SELECT * FROM t_product WHERE product_id = #{productId}")
    ProductEntity selectByProductId(Long productId);

    @Select ("SELECT * FROM t_product WHERE product_name = #{productName}")
    ProductEntity selectByProductName(String productName);

    @Select ("SELECT * FROM t_product WHERE product_category = #{productCategory}")
    List<ProductEntity> selectByProductCategory(String productCategory);

    @Select ("SELECT * FROM t_product WHERE product_brand = #{productBrand}")
    List<ProductEntity> selectByProductBrand(String productBrand);

    @Select ("SELECT * FROM t_product WHERE product_price BETWEEN #{minPrice} AND #{maxPrice}")
    List<ProductEntity> selectByProductPriceRange(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice);

    @Select ("SELECT * FROM t_product WHERE product_stock >= #{minStock}")
    List<ProductEntity> selectByProductStockGreaterThanEqual(int minStock);

    @Select ("SELECT * FROM t_product WHERE product_stock <= #{maxStock}")
    List<ProductEntity> selectByProductStockLessThanEqual(int maxStock);

    @Select ("SELECT * FROM t_product WHERE product_description LIKE CONCAT('%', #{keyword}, '%')")
    List<ProductEntity> selectByProductDescriptionContaining(String keyword);

    @Select ("SELECT * FROM t_product WHERE product_image = #{productImage}")
    List<ProductEntity> selectByProductImage(String productImage);

    @Select ("SELECT * FROM t_product WHERE product_name LIKE CONCAT('%', #{keyword}, '%') OR product_description LIKE CONCAT('%', #{keyword}, '%')")
    List<ProductEntity> searchProductsByKeyword(String keyword);

    @Select ("SELECT * FROM t_product WHERE product_category = #{productCategory} AND product_price BETWEEN #{minPrice} AND #{maxPrice}")
    List<ProductEntity> selectByCategoryAndPriceRange(String productCategory, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice);

    @Select ("SELECT * FROM t_product WHERE product_brand = #{productBrand} AND product_stock >= #{minStock}")
    List<ProductEntity> selectByBrandAndMinStock(String productBrand, int minStock);

    @Select ("SELECT * FROM t_product WHERE product_category = #{productCategory} AND product_brand = #{productBrand} AND product_price BETWEEN #{minPrice} AND #{maxPrice}")
    List<ProductEntity> selectByCategoryBrandAndPriceRange(String productCategory, String productBrand, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice);

    @Select ("SELECT * FROM t_product WHERE product_name = #{productName} AND product_category = #{productCategory} AND product_brand = #{productBrand} AND product_price BETWEEN #{minPrice} AND #{maxPrice} AND product_stock >= #{minStock}")
    List<ProductEntity> selectByMultipleCriteria(String productName, String productCategory, String productBrand, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, int minStock);


}
