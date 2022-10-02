package backend.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.ecommerce.domain.Order;
import backend.ecommerce.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(String orderId);

    List<Order> findByUser(User user);

}
