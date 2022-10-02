package backend.ecommerce.service;

import java.util.List;

import backend.ecommerce.domain.Cart;
import backend.ecommerce.exception.domain.ProductExistInCartException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;

public interface CartService {

    Cart saveProductInCart(String productId, String email)
            throws ProductExistInCartException, UserNotFoundException, ProductNotFoundException;

    List<Cart> getCart(String email);

    void increaseQuantity(String cartId) throws ProductNotFoundException;

    void decreaseQuantity(String cartId) throws ProductNotFoundException, Exception;

    void deleteCart(String cartId) throws ProductNotFoundException;

    void deleteAllItemsInCart(String email) throws UserNotFoundException;

}
