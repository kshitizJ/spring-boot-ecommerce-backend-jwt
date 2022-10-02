package backend.ecommerce.resource;

import static backend.ecommerce.constant.FileConstant.FORWARD_SLASH;
import static backend.ecommerce.constant.FileConstant.PRODUCT_FOLDER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.ecommerce.domain.HttpResponse;
import backend.ecommerce.domain.Product;
import backend.ecommerce.exception.ExceptionHandling;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = { "http://localhost:3000**" })
@RestController
@RequestMapping(path = "/api/v1/products")
@Slf4j
public class ProductResource extends ExceptionHandling {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        log.info("Getting all the products.");
        return new ResponseEntity<>(productService.getAllProducts(), OK);
    }

    @GetMapping(path = "/image/{productName}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProductImage(@PathVariable("productName") String productName,
            @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(PRODUCT_FOLDER + productName + FORWARD_SLASH + fileName));
    }

    @PostMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestBody Map<String, Object> product)
            throws ProductNotFoundException {
        log.info("Getting the product: " + (String) product.get("productId"));
        return new ResponseEntity<>(productService.getProduct((String) product.get("productId")), OK);
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('product:create')")
    public ResponseEntity<Product> addProduct(@RequestBody Map<String, Object> product) throws IOException {
        log.info("Adding the product.");
        return new ResponseEntity<>(
                productService.addProduct(
                        (String) product.get("productName"),
                        (String) product.get("productDescription"),
                        (String) product.get("productSubDescription"),
                        BigDecimal.valueOf((double) product.get("productPrice")),
                        (MultipartFile) product.get("productImage")),
                CREATED);
    }

    @PostMapping("/updateProduct")
    @PreAuthorize("hasAuthority('product:update')")
    public ResponseEntity<HttpResponse> updateProduct(@RequestBody Map<String, Object> product)
            throws ProductNotFoundException, IOException {
        log.info("Updating the product with product Id: " + product.get("productId"));
        productService.updateProduct(
                (String) product.get("productId"),
                (String) product.get("productName"),
                (String) product.get("productDescription"),
                (String) product.get("productSubDescription"),
                BigDecimal.valueOf((double) product.get("productPrice")),
                (MultipartFile) product.get("productImage"));
        return response(CREATED, "Product updated successfully.");
    }

    @DeleteMapping("/deleteProduct")
    @PreAuthorize("hasAuthority('product:delete')")
    public ResponseEntity<HttpResponse> deleteProduct(@RequestBody Map<String, Object> product)
            throws ProductNotFoundException {
        log.info("Deleting the product: " + (String) product.get("productId"));
        productService.deleteProduct((String) product.get("productId"));
        return response(OK, "Product deleted successfully.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }

}
