package backend.ecommerce.service;

import java.util.List;

import backend.ecommerce.domain.Order;
import backend.ecommerce.exception.domain.CartEmptyException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;

public interface OrderService {

    void addOrder(String email) throws CartEmptyException, UserNotFoundException;

    List<Order> getOrders(String email) throws UserNotFoundException;

    Order completeOrder(String orderId) throws ProductNotFoundException;

}
