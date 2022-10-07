package backend.ecommerce.repository;

import static backend.ecommerce.enumeration.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import backend.ecommerce.domain.User;

// @DataJpaTest
// @AutoConfigureTestDatabase(replace = NONE)
// @Rollback(false)
@SpringBootTest
public class UserRepositoryTest {

   // @Autowired
   // private TestEntityManager testEntityManager;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Test
   void isUserExistById() {
      User user = new User();
      user.setUserId(generateUserId());
      user.setPassword(passwordEncoder.encode("password"));
      user.setFirstName("firstName");
      user.setLastName("lastName");
      user.setEmail("email");
      user.setUsername("username");
      user.setRegisterDate(LocalDateTime.now());
      user.setIsActive(true);
      user.setIsNotLocked(true);
      user.setRole(ROLE_USER.name());
      user.setAuthorities(ROLE_USER.getAuthorities());
      user.setProfileImageUrl(null);
      User savedUser = userRepository.save(user);
      System.out.println(savedUser.getUserId());
      Boolean flag = userRepository.isUserExistById(savedUser.getUserId());
      assertThat(flag).isTrue();
   }

   @AfterEach
   void tearDown() {
      System.out.println("After each is running");
   }

   @BeforeEach
   void setUp() {
      System.out.println("Before each is running");
   }

   private String generateUserId() {
      return RandomStringUtils.randomAlphanumeric(10);
   }

}
