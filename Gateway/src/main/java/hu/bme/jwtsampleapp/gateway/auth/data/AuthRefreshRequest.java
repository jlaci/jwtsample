package hu.bme.jwtsampleapp.gateway.auth.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthRefreshRequest implements Serializable {

    private Long userId;

    private String refreshToken;

}