package hu.bme.jwtsampleapp.gateway.user;


import hu.bme.jwtsampleapp.entities.user.Role;
import hu.bme.jwtsampleapp.entities.user.entity.User;
import hu.bme.jwtsampleapp.entities.user.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO: disable in production version!
    @PostConstruct
    public void injectDummyCustomers() {
        try {
            log.info("Injecting test user!");

            User admin;
            try {
                admin = new User("Admin", "1234");
                admin.setRoles(Collections.singletonList(new UserRole(admin, Role.ROLE_ADMIN)));
                create(admin);
            } catch (UserAlreadyExistsException e) {
                log.debug("User already in database");
            }

            User user;
            try {
                user = new User("User", "1234");
                user.setRoles(Collections.singletonList(new UserRole(user, Role.ROLE_USER)));
                create(user);
            } catch (UserAlreadyExistsException e) {
                log.debug("User already in database");
            }
        } catch (Exception e) {
            log.warn("Failed to inject dummy data!", e);
        }
    }

    public User create (User user) throws UserAlreadyExistsException   {

        if (userRepository.countByUsername(user.getUsername()) > 0){
            throw new UserAlreadyExistsException();
        }

        if (user.getUsername() == null
                || user.getUsername().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getRoles() == null
                || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User login(String username, String password) throws BadCredentialsException{
        User user = userRepository.findByUsername(username);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())){
                return user;
            } else {
                throw new BadCredentialsException("Wrong password");
            }
        }

        throw new BadCredentialsException("Username not in database");
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public class UserAlreadyExistsException extends RuntimeException {
    }
}
