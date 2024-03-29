package backend.ecommerce.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.servlet.NoHandlerFoundException;

import com.auth0.jwt.exceptions.TokenExpiredException;

import backend.ecommerce.domain.HttpResponse;
import backend.ecommerce.exception.domain.CartEmptyException;
import backend.ecommerce.exception.domain.EmailExistException;
import backend.ecommerce.exception.domain.EmailNotFoundException;
import backend.ecommerce.exception.domain.ProductExistInCartException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.exception.domain.UsernameExistException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @RestControllerAdvice will allow this class to somewhat behave like a
 *                       controller but just to handle exception
 * 
 *                       <p>
 *                       We are implementing ErrorController interface so that
 *                       we can give our own exception error when there is no
 *                       method mapping for any random endpoint
 *                       </p>
 * 
 */

@RestControllerAdvice
@Slf4j
public class ExceptionHandling implements ErrorController {
    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration.";
    private static final String METHOD_IS_NOT_ALLOWED = "This request is not allowed on this endpoint. Please send a '%s' request.";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing the request.";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again.";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration.";
    private static final String ERROR_PROCESSING_FILE = "Error occured while processing file.";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission.";
    public static final String ERROR_PATH = "/error";

    /**
     * 
     * Here we are setting our own error message for "/error" endpoint. When we call
     * a random url which does not have a mapping we show our custom 404 json error
     * instead of white label error
     * 
     */
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this url.");
    }

    /**
     * 
     * @ExceptionHandler will tell that this is a handler method for that particular
     *                   exception class.
     * 
     * @return HttpResponse which contains the exception message and status.
     * 
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        log.error(ACCOUNT_DISABLED);
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        log.error(INCORRECT_CREDENTIALS);
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        log.error(NOT_ENOUGH_PERMISSION);
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        log.error(ACCOUNT_LOCKED);
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ProductExistInCartException.class)
    public ResponseEntity<HttpResponse> productExistInCartException(ProductExistInCartException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HttpResponse> productNotFoundException(ProductNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<HttpResponse> bartEmptyException(CartEmptyException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * 
     * To enble this exception we first need to enable
     * 'throw-exception-if-no-handler-found' and than we need
     * to disable 'add-mappings' in appilication.yml file.
     * 
     * <p>
     * We are commenting this function because we don't want our no handler
     * exception class to override every error that is found, just for sake of
     * removal of white label error page.
     * </p>
     * 
     * @param exception
     * @return
     */
    // @ExceptionHandler(NoHandlerFoundException.class)
    // public ResponseEntity<HttpResponse>
    // noHandlerFoundException(NoHandlerFoundException exception) {

    // log.error(exception.getMessage());
    // return createHttpResponse(BAD_REQUEST, "This endpoint of api was never
    // created.");

    // }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        // Getting the supported method from the exception and saving it in HttpMethod's
        // instance.
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        log.error(String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(),
                message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

}
