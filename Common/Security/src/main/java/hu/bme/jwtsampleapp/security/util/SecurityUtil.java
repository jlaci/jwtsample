package hu.bme.jwtsampleapp.security.util;

import hu.bme.jwtsampleapp.entities.user.Role;
import hu.bme.jwtsampleapp.security.spring.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;

public class SecurityUtil {


    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.name())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Long getUserId() {
        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return userAuthentication.getClaims().getId();
    }

    public static String randomSecureString(int length) {
        SecureRandom random = new SecureRandom();
        String acceptedChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(acceptedChars.charAt(random.nextInt(acceptedChars.length())));
        }

        return sb.toString();
    }

}
