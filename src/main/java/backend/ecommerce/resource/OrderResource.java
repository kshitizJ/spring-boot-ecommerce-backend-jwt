package backend.ecommerce.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.ecommerce.domain.HttpResponse;
import backend.ecommerce.domain.Order;
import backend.ecommerce.exception.ExceptionHandling;
import backend.ecommerce.exception.domain.CartEmptyException;
import backend.ecommerce.exception.domain.ProductNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = { "http://localhost:3000**" })
@RestController
@RequestMapping(path = "/api/v1/order")
@Slf4j
public class OrderResource extends ExceptionHandling {

    @Autowired
    private OrderService orderService;

    public ResponseEntity<List<Order>> getOrders(@RequestBody String email) throws UserNotFoundException {
        log.info("Getting orders of user: " + email);
        return new ResponseEntity<>(orderService.getOrders(email), HttpStatus.OK);
    }

    public ResponseEntity<HttpResponse> addOrder(@RequestBody String email)
            throws CartEmptyException, UserNotFoundException {
        log.info("Adding the orders of email: " + email);
        orderService.addOrder(email);
        return response(HttpStatus.OK, "Order placed successfully.");
    }

    public ResponseEntity<HttpResponse> completeOrder(@RequestBody String orderId) throws ProductNotFoundException {
        log.info("Order completed");
        orderService.completeOrder(orderId);
        return response(HttpStatus.OK, "Order completed successfully");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }

}
