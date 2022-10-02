package backend.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.ecommerce.domain.Cart;
import backend.ecommerce.domain.Product;
import backend.ecommerce.domain.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductAndUser(Product product, User user);

    Cart findByCartId(String cartId);

    List<Cart> findByUser(User user);
}
