package hu.bme.jwtsampleapp.entities.user.claims;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserClaimsMapper {

    public UserClaims mapTo(Claims claims) {

        UserClaims userClaims = new UserClaims();
        userClaims.setId(Long.parseLong(claims.getSubject()));
        userClaims.setUsername((String) claims.get(UserClaims.USERNAME_KEY));
        userClaims.setRoles((List<String>) claims.get(UserClaims.ROLES_KEY));
        userClaims.setExpiration(claims.getExpiration());

        return userClaims;
    }

}