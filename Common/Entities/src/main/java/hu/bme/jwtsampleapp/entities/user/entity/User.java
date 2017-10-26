package hu.bme.jwtsampleapp.entities.user.entity;

import hu.bme.jwtsampleapp.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "jwt_sample_user")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email, List<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
