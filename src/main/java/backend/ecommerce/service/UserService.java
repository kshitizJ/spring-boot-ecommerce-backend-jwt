package backend.ecommerce.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import backend.ecommerce.domain.User;
import backend.ecommerce.exception.domain.EmailExistException;
import backend.ecommerce.exception.domain.EmailNotFoundException;
import backend.ecommerce.exception.domain.UserNotFoundException;
import backend.ecommerce.exception.domain.UsernameExistException;

public interface UserService {

        User register(String firstName, String lastName, String username, String email, String password)
                        throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

        List<User> getUsers();

        User findUserByUsername(String username);

        User findUserByEmail(String email);

        User addNewUser(String firstName, String lastName, String username, String email, String role,
                        boolean isNonLocked, boolean isActive, MultipartFile profileImage)
                        throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

        User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
                        String newEmail, String newRole, boolean isNonLocked, boolean isActive,
                        MultipartFile newProfileImage)
                        throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

        User updateProfileImage(String username, MultipartFile profileImage)
                        throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

        void deleteUser(String userId);

        void resetPassword(String email) throws EmailNotFoundException, MessagingException;

}
