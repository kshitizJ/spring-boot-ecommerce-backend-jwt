package backend.ecommerce.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.ecommerce.domain.Cart;
import backend.ecommerce.domain.Product;
import backend.ecommerce.domain.User;
import backend.ecommerce.exception.domain.ProductExistInCartException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.repository.CartRepository;
import backend.ecommerce.repository.ProductRepository;
import backend.ecommerce.repository.UserRepository;
import backend.ecommerce.service.CartService;

@Service
@Transactional
public class CartServiceImplementation implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart saveProductInCart(String productId, String email)
            throws ProductExistInCartException, UserNotFoundException, ProductNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserNotFoundException("User does not exist!");
        Product product = productRepository.findProductByProductId(productId);
        if (product == null)
            throw new ProductNotFoundException("Product doesnot exist!");
        Cart existingCart = cartRepository.findByProductAndUser(product, user);
        if (existingCart != null)
            throw new ProductExistInCartException("Product already exist in cart.");
        Cart cart = new Cart();
        cart.setCartId(generateCartId());
        cart.setQuantity(1);
        cart.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        cart.setProduct(product);
        cart.setUser(user);
        cart.setProductAddedDate(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCart(String email) {
        User user = userRepository.findUserByEmail(email);
        return cartRepository.findByUser(user);
    }

    @Override
    public void increaseQuantity(String cartId) throws ProductNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId);
        if (cart == null)
            throw new ProductNotFoundException("Product in cart not found");
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setSubTotal(cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        cartRepository.save(cart);
    }

    @Override
    public void decreaseQuantity(String cartId) throws Exception {
        Cart cart = cartRepository.findByCartId(cartId);
        if (cart == null)
            throw new ProductNotFoundException("Product in cart not found");
        if (cart.getQuantity() - 1 <= 0)
            throw new Exception("Product quantity cannot be 0");
        cart.setQuantity(cart.getQuantity() - 1);
        cart.setSubTotal(cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(String cartId) throws ProductNotFoundException {
        Cart cart = cartRepository.findByCartId(cartId);
        if (cart == null)
            throw new ProductNotFoundException("Product in cart not found");
        cartRepository.delete(cart);
    }

    @Override
    public void deleteAllItemsInCart(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserNotFoundException("User does not exist!");
        List<Cart> carts = cartRepository.findByUser(user);
        for (Cart cart : carts) {
            cartRepository.delete(cart);
        }
    }

    private String generateCartId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

}
