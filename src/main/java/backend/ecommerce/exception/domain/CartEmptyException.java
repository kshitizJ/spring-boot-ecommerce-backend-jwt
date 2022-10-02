package backend.ecommerce.exception.domain;

public class CartEmptyException extends Exception {
    public CartEmptyException(String message) {
        super(message);
    }
}
