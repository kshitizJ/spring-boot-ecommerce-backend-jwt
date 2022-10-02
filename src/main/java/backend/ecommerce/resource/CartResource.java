package backend.ecommerce.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.ecommerce.domain.Cart;
import backend.ecommerce.domain.HttpResponse;
import backend.ecommerce.exception.ExceptionHandling;
import backend.ecommerce.exception.domain.ProductExistInCartException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.service.CartService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = { "http://localhost:3000**" })
@RestController
@RequestMapping(path = "/api/v1/cart")
@Slf4j
public class CartResource extends ExceptionHandling {

    @Autowired
    private CartService cartService;

    @GetMapping()
    public ResponseEntity<List<Cart>> getMethodName(@RequestBody String email) {
        log.info("Getting the items from the cart.");
        return new ResponseEntity<>(cartService.getCart(email), OK);
    }

    @PostMapping("/addItemInCart")
    public ResponseEntity<Cart> addProductInCart(@RequestBody Map<String, String> cartItem)
            throws ProductExistInCartException, UserNotFoundException, ProductNotFoundException {
        log.info("Adding the product in the cart.");
        return new ResponseEntity<>(cartService.saveProductInCart(cartItem.get("productId"), cartItem.get("email")),
                OK);
    }

    @PostMapping("/increaseQuantity")
    public ResponseEntity<HttpResponse> increaseCartQuantitiy(@RequestBody String cartId)
            throws ProductNotFoundException {
        log.info("Increasing the cart quantitiy");
        cartService.increaseQuantity(cartId);
        return response(OK, "Increased the cart quantity.");
    }

    @PostMapping("/decreaseQuantity")
    public ResponseEntity<HttpResponse> decreaseCartQuantitiy(@RequestBody String cartId)
            throws Exception {
        log.info("Increasing the cart quantitiy");
        cartService.decreaseQuantity(cartId);
        return response(OK, "Increased the cart quantity.");
    }

    @PostMapping("/deleteCartItem")
    public ResponseEntity<HttpResponse> deleteCart(@RequestBody String cartId) throws ProductNotFoundException {
        log.info("Deleting the product from cart.");
        cartService.deleteCart(cartId);
        return response(OK, "Cart item deleted successfully.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }

}
