package Tech.Spring.usermanagement.resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Tech.Spring.usermanagement.exception.UserNotFoundException;
import Tech.Spring.usermanagement.model.User;
import Tech.Spring.usermanagement.repo.UserRepo;
import Tech.Spring.usermanagement.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserResource {
    @GetMapping("/users")
    public String  hello() {
        return "This is the first try";
    }

    @Autowired
    UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    UserRepo userRepo;

    @PostMapping("/addUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepo
                    .save(new User(user.getName(), user.getEmail(), user.getPassword(), true));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String title) {
        try {
            List<User> users = new ArrayList<User>();

            if (title == null)
                userRepo.findAll().forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepo.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());
            _user.setisActive(user.isActive());
            return new ResponseEntity<>(userRepo.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public User findEmployeeById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable("id") Long id) {
        User user = findEmployeeById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}