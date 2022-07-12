package Tech.Spring.usermanagement.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import Tech.Spring.usermanagement.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);
}

// JpaRepository helps in finding data