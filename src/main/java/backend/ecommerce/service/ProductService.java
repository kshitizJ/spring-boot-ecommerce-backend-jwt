package backend.ecommerce.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import backend.ecommerce.domain.Comment;
import backend.ecommerce.domain.Product;
import backend.ecommerce.exception.domain.ProductNotFoundException;

public interface ProductService {

        Product getProduct(String productId) throws ProductNotFoundException;

        Product addProduct(String name, String description, String subDescription, BigDecimal price,
                        MultipartFile image) throws IOException;

        Product updateProduct(String productId, String name, String description, String subDescription,
                        BigDecimal price, MultipartFile image) throws ProductNotFoundException, IOException;

        void deleteProduct(String productId) throws ProductNotFoundException;

        List<Product> getAllProducts();

        List<Product> getAllProductsWithPriceBelow(BigDecimal price);

        List<Product> getAllProductsWithPriceAbove(BigDecimal price);

        List<Product> getAllProductByPriceRange(BigDecimal priceBefore, BigDecimal priceAfter);

        List<Product> getAllProductsWithRating(Integer rating);

        List<Product> getAllProductsWithRatingAbove(Integer rating);

        List<Comment> getAllComments(String productId) throws ProductNotFoundException;

}
