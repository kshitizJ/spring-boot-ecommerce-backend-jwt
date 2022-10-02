package backend.ecommerce.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.ecommerce.domain.Cart;
import backend.ecommerce.domain.Order;
import backend.ecommerce.domain.User;
import backend.ecommerce.exception.domain.CartEmptyException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.repository.OrderRepository;
import backend.ecommerce.repository.UserRepository;
import backend.ecommerce.service.CartService;
import backend.ecommerce.service.OrderService;

@Service
@Transactional
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Override
    public void addOrder(String email) throws CartEmptyException, UserNotFoundException {
        List<Cart> carts = cartService.getCart(email);
        if (carts == null)
            throw new CartEmptyException("Your cart is empty!");
        for (Cart cart : carts) {
            Order order = new Order();
            order.setOrderId(generateOrderId());
            order.setQuantity(cart.getQuantity());
            order.setSubTotal(cart.getSubTotal());
            order.setStatus(false);
            order.setProduct(cart.getProduct());
            order.setUser(cart.getUser());
            order.setOrderDate(LocalDateTime.now());
            orderRespository.save(order);
        }
        cartService.deleteAllItemsInCart(email);
    }

    @Override
    public List<Order> getOrders(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserNotFoundException("User not found!");
        return orderRespository.findByUser(user);
    }

    @Override
    public Order completeOrder(String orderId) throws ProductNotFoundException {
        Order order = orderRespository.findByOrderId(orderId);
        if (order == null)
            throw new ProductNotFoundException("Product not found!");
        order.setStatus(true);
        return orderRespository.save(order);
    }

    private String generateOrderId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

}
