package backend.ecommerce;

import static backend.ecommerce.constant.FileConstant.PRODUCT_FOLDER;
import static backend.ecommerce.constant.FileConstant.USER_FOLDER;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import backend.ecommerce.service.UserService;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
		new File(USER_FOLDER).mkdirs();
		new File(PRODUCT_FOLDER).mkdir();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.addNewUser("Kshitiz", "Jain", "kshitizJ", "coolkshitiz@mail.com", "ROLE_SUPER_ADMIN", true,
					true, null);
		};
	}

}
