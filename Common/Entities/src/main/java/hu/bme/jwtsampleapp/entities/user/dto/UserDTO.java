package hu.bme.jwtsampleapp.entities.user.dto;


import hu.bme.jwtsampleapp.entities.BaseDTO;
import hu.bme.jwtsampleapp.entities.user.entity.User;
import hu.bme.jwtsampleapp.entities.user.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO implements BaseDTO {

    private String id;

    private String username;

    private String password;

    private List<String> roles;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = new ArrayList<>();
        for (UserRole userRole : user.getRoles()) {
            roles.add(userRole.getRole().name());
        }
        if (user.getId() != null) {
            this.setId(user.getId().toString());
        }
    }
}