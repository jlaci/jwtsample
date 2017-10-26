package hu.bme.jwtsampleapp.gateway.auth.service;


import hu.bme.jwtsampleapp.entities.user.entity.User;
import hu.bme.jwtsampleapp.gateway.auth.data.RefreshToken;
import hu.bme.jwtsampleapp.gateway.auth.data.AuthRefreshRequest;
import hu.bme.jwtsampleapp.gateway.auth.data.AuthRequest;
import hu.bme.jwtsampleapp.gateway.auth.data.AuthResponse;
import hu.bme.jwtsampleapp.gateway.auth.exception.AuthenticationException;
import hu.bme.jwtsampleapp.gateway.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Autowired
    private TokenStorageService tokenStorageService;

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            log.debug("Authenticating {} : {}", authRequest.getUsername(), authRequest.getPassword().replaceAll(".", "*"));
            User user = userService.login(authRequest.getUsername(), authRequest.getPassword());
            return createAndStoreTokens(user);
        } catch (Exception e) {
            throw new BadUsernameOrPasswordException();
        }
    }

    public void logout(Long userId) {
        log.debug("Logging out user {} ", userId);
        tokenStorageService.invalidateRefreshToken(userId);
    }

    public AuthResponse refresh(AuthRefreshRequest refreshRequest) {
        try {
            String providedRefreshToken = refreshRequest.getRefreshToken();
            RefreshToken storedRefreshToken = tokenStorageService.getRefreshToken(refreshRequest.getUserId());

            // If there is no stored refresh toke or it expired
            if (storedRefreshToken == null || storedRefreshToken.getExpiration().before(new Date())) {
                log.debug("No valid refresh token found for user!");
                throw new InvalidTokenException();
            } else {
                if (storedRefreshToken.getToken().equals(providedRefreshToken)) {
                    User user = userService.findOne(refreshRequest.getUserId());
                    if (user == null) {
                        throw new AuthenticationException("User not found!");
                    }
                    return createAndStoreTokens(user);
                } else {
                    throw new InvalidTokenException();
                }
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private AuthResponse createAndStoreTokens(User user) {
        AuthResponse response = tokenGeneratorService.createTokens(user);
        tokenStorageService.storeRefreshToken(user.getId(), response.getRefreshToken(), response.getRefreshTokenExpireDate());
        return response;
    }

    public class BadUsernameOrPasswordException extends AuthenticationException {

    }

    public class InvalidTokenException extends AuthenticationException {

    }
}
