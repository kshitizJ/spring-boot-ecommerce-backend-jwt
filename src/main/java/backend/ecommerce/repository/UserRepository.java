package backend.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.ecommerce.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(String userId);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    @Query("select u from User u where u.registerDate < ?1  and u.role = ?2")
    List<User> findAllByRegisterDateBefore(LocalDateTime registerDate, String role);

    @Query("select u from User u where u.registerDate > ?1  and u.role = ?2")
    List<User> findAllByRegisterDateAfter(LocalDateTime registerDate, String role);

    @Query("select u from User u where u.registerDate between ?1 and ?2 and u.role = ?3 order by u.registerDate")
    List<User> findByRegisterDate(LocalDateTime beforeDate, LocalDateTime afterDate, String role);

    @Query("select u from User u where u.role = ?1")
    List<User> findUsersByRole(String role);

    void deleteByUserId(String userId);

    @Query("select COUNT(u) from User u where u.registerDate between ?1 and ?2 and u.role = ?3 order by u.registerDate")
    Long countByRegisterDate(LocalDateTime beforeDate, LocalDateTime afterDate, String role);

    Long countByRole(String role);

    Long countByIsActive(Boolean isActive);

    Long countByIsNotLocked(Boolean isNotLocked);

    @Query("select case when COUNT(u) > 0 then true else false end from User u where u.userId = ?1")
    Boolean isUserExistById(String userId);

}
