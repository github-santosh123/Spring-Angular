package Tech.Spring.usermanagement.repo;
import java.util.Optional;
import Tech.Spring.usermanagement.model.UserRole;
import Tech.Spring.usermanagement.model.Role;

import Tech.Spring.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}