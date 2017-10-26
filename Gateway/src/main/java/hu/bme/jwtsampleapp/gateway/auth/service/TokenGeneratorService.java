package hu.bme.jwtsampleapp.gateway.auth.service;


import hu.bme.jwtsampleapp.entities.user.claims.UserClaims;
import hu.bme.jwtsampleapp.entities.user.entity.User;
import hu.bme.jwtsampleapp.entities.user.entity.UserRole;
import hu.bme.jwtsampleapp.gateway.auth.data.AuthResponse;
import hu.bme.jwtsampleapp.security.util.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TokenGeneratorService {

    @Value("${jwt.accessTokenValidity}")
    private Long accessTokeValidity;

    @Value("${jwt.refreshTokenLength}")
    private Integer refreshTokenLength;

    @Value("${jwt.refreshTokenValidity}")
    private Long refreshTokenValidity;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Create the access and refresh tokens for the given user
     * @param entity
     * @return
     */
    public AuthResponse createTokens(User entity) {
        List<String> roles = new ArrayList<>();

        for(UserRole userRole : entity.getRoles()) {
            roles.add(userRole.getRole().name());
        }

        Date accessTokenExpireDate = new Date(new Date().getTime() + accessTokeValidity);
        String accessToken = Jwts.builder()
                .setSubject(entity.getId().toString())
                .setExpiration(accessTokenExpireDate)
                .claim(UserClaims.ROLES_KEY, roles)
                .claim(UserClaims.USERNAME_KEY, entity.getUsername())
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

        String refreshToken = SecurityUtil.randomSecureString(refreshTokenLength);
        Date refreshTokenExpireDate = new Date(new Date().getTime() + refreshTokenValidity);

        return new AuthResponse(accessToken, accessTokenExpireDate, refreshToken, refreshTokenExpireDate, entity);
    }



}
