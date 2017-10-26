package hu.bme.jwtsampleapp.entities.user.claims;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserClaims implements Serializable {

    public static final String USERNAME_KEY = "username";
    public static final String ROLES_KEY = "roles";
    public static final String EXPIRATION_KEY = "expireDate";

    private Long id;

    private String username;

    private List<String> roles;

    private Date expiration;

    public UserClaims() {

    }

    public UserClaims(Long id, String username, List<String> roles, Date expiration) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.expiration = expiration;
    }
}