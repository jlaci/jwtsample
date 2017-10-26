package hu.bme.jwtsampleapp.gateway.auth.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RefreshToken {

    private String token;

    private Date expiration;

    public RefreshToken() {
    }

    public RefreshToken(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
