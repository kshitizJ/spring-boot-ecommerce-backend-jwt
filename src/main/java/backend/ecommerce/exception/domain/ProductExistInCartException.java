package backend.ecommerce.exception.domain;

public class ProductExistInCartException extends Exception {
    public ProductExistInCartException(String message) {
        super(message);
    }
}
