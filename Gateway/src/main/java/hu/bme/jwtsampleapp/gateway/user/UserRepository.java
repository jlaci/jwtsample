package hu.bme.jwtsampleapp.gateway.user;


import hu.bme.jwtsampleapp.entities.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    long countByUsername(String username);

}
