package backend.ecommerce.service.implementation;

import static backend.ecommerce.constant.FileConstant.DIRECTORY_CREATED;
import static backend.ecommerce.constant.FileConstant.DOT;
import static backend.ecommerce.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static backend.ecommerce.constant.FileConstant.FORWARD_SLASH;
import static backend.ecommerce.constant.FileConstant.JPG_EXTENSION;
import static backend.ecommerce.constant.FileConstant.PRODUCT_FOLDER;
import static backend.ecommerce.constant.FileConstant.PRODUCT_IMAGE_PATH;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import backend.ecommerce.domain.Comment;
import backend.ecommerce.domain.Product;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.repository.ProductRepository;
import backend.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(String productId) throws ProductNotFoundException {
        Product product = productRepository.findProductByProductId(productId);
        if (product == null)
            throw new ProductNotFoundException("Product does not exist!");
        log.info("Getting the product from database.");
        return product;
    }

    @Override
    public Product addProduct(String name, String description, String subDescription, BigDecimal price,
            MultipartFile image) throws IOException {

        log.info("Adding the product in database.");
        Product product = new Product();
        product.setProductId(generateProductId());
        product.setName(name);
        product.setDescription(description);
        product.setSubDescription(subDescription);
        product.setPrice(price);
        product.setRating(0);
        product.setAddedDate(LocalDateTime.now());
        product.setLastModified(LocalDateTime.now());
        setProductImage(product, image);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String productId, String name, String description, String subDescription,
            BigDecimal price, MultipartFile image) throws ProductNotFoundException, IOException {
        Product product = getProduct(productId);
        product.setName(name);
        product.setDescription(description);
        product.setSubDescription(subDescription);
        product.setPrice(price);
        product.setRating(0);
        product.setLastModified(LocalDateTime.now());
        setProductImage(product, image);
        log.info("Updating the product in the database.");
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        log.info("Deleting the product from the database.");
        productRepository.delete(product);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all the products from the database.");
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsWithPriceBelow(BigDecimal price) {
        return productRepository.findProductByPriceBelow(price);
    }

    @Override
    public List<Product> getAllProductsWithPriceAbove(BigDecimal price) {
        return productRepository.findProductByPriceAbove(price);
    }

    @Override
    public List<Product> getAllProductByPriceRange(BigDecimal priceBefore, BigDecimal priceAfter) {
        return productRepository.findProductByPriceRange(priceBefore, priceAfter);
    }

    @Override
    public List<Product> getAllProductsWithRating(Integer rating) {
        return productRepository.findAllProductByRating(rating);
    }

    @Override
    public List<Product> getAllProductsWithRatingAbove(Integer rating) {
        return productRepository.findAllProductByRatingAbove(rating);
    }

    @Override
    public List<Comment> getAllComments(String productId) throws ProductNotFoundException {
        Product product = getProduct(productId);
        return product.getComments();
    }

    private String generateProductId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void setProductImage(Product product, MultipartFile image) throws IOException {
        log.info("Checking if the file is empty or not.");
        if (image != null) {
            // This Path will be like this:
            // kshitiz/home/college-work/spring boot/e-commerce
            // pbl/backend/src/main/resources/static/images/product/
            Path productFolder = Paths.get(PRODUCT_FOLDER, product.getName()).toAbsolutePath().normalize();

            log.info("Checking if the folder exist or not.");
            if (!Files.exists(productFolder)) {
                Files.createDirectories(productFolder);
                log.info(DIRECTORY_CREATED + product.getName());
            }

            // If file exist then we delete the existed file
            Files.deleteIfExists(
                    Paths.get(productFolder + product.getName() + DOT + JPG_EXTENSION));

            // Saving the actual file inside the folder. REPLACE_EXISTING = will replace the
            // existing picture
            Files.copy(image.getInputStream(), productFolder.resolve(product.getName() + DOT + JPG_EXTENSION),
                    REPLACE_EXISTING);

            // Setting the product's image url
            product.setProductImage(setProductImageUrl(product.getName()));
            log.info(FILE_SAVED_IN_FILE_SYSTEM);
        } else
            log.warn("No image found!");
    }

    /**
     * 
     * this is going to give us the base like
     * 'http://localhost:8080//product/image/product.jpg'
     * 
     * <p
     * So ServletUriComponentsBuilder.fromCurrentContextPath() will return what ever
     * the base url is, i.e. 'http://localhost:8080/'.
     * </p>
     * 
     * <p>
     * And .path() function will add the path to the base url.
     * </p>
     * 
     */
    private String setProductImageUrl(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PRODUCT_IMAGE_PATH + FORWARD_SLASH + name + DOT + JPG_EXTENSION)
                .toUriString();
    }

}
