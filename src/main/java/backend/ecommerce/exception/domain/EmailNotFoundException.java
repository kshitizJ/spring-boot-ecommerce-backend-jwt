package backend.ecommerce.exception.domain;

public class EmailNotFoundException extends Exception {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
