package hu.bme.jwtsampleapp.security.spring.remoting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class SecureRequestWrapper {

    private Object value;

    private Authentication authentication;

    public SecureRequestWrapper() {
    }

    public SecureRequestWrapper(Object value, Authentication authentication) {
        this.value = value;
        this.authentication = authentication;
    }
}
