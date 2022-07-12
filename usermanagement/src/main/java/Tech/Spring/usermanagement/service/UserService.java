package Tech.Spring.usermanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Tech.Spring.usermanagement.exception.UserNotFoundException;
import Tech.Spring.usermanagement.model.User;
import Tech.Spring.usermanagement.repo.UserRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteUserById(id);
    }
}