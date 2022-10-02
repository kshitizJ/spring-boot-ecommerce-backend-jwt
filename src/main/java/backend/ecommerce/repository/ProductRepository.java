package backend.ecommerce.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.ecommerce.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.productId = ?1")
    Product findProductByProductId(String productId);

    @Query("select p from Product p where p.addedDate between ?1 and ?2 order by p.addedDate")
    List<Product> findAllProductByAddedDate(LocalDateTime beforeDate, LocalDateTime afterDate);

    @Query("select p from Product p where p.rating = ?1")
    List<Product> findAllProductByRating(Integer rating);

    @Query("select p from Product p where p.rating > ?1")
    List<Product> findAllProductByRatingAbove(Integer rating);

    @Query("select p from Product p where p.price between ?1 and ?2 order by p.price")
    List<Product> findProductByPriceRange(BigDecimal priceBefore, BigDecimal priceAfter);

    @Query("select p from Product p where p.price < ?1")
    List<Product> findProductByPriceBelow(BigDecimal price);

    @Query("select p from Product p where p.price > ?1")
    List<Product> findProductByPriceAbove(BigDecimal price);

    @Query("select count(p) from Product p")
    Long countProducts();

}
