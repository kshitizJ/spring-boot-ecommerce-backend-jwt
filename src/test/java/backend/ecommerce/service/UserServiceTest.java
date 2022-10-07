package backend.ecommerce.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import backend.ecommerce.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

   @Mock
   private UserRepository userRepository;

   @Autowired
   private UserService userService;

   // @BeforeEach
   // void setUp() {
   // this.userService = new UserServiceImplementation(userRepository);
   // }

   @Test
   void getAllUsers() {
      userService.getUsers();
      verify(userRepository).findAll();
   }
}
