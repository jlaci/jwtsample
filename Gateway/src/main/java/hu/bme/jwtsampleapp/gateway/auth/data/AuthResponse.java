package hu.bme.jwtsampleapp.gateway.auth.data;

import hu.bme.jwtsampleapp.entities.user.dto.UserDTO;
import hu.bme.jwtsampleapp.entities.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AuthResponse implements Serializable {

    private String accessToken;

    private Date accessTokenExpireDate;

    private String refreshToken;

    private Date refreshTokenExpireDate;

    private UserDTO user;

    public AuthResponse(String accessToken, Date accessTokenExpireDate, String refreshToken, Date refreshTokenExpireDate, User user) {
        this.accessToken = accessToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
        this.user = new UserDTO(user);
    }

}
