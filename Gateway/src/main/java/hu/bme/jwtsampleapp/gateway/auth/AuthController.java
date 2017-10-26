package hu.bme.jwtsampleapp.gateway.auth;

import hu.bme.jwtsampleapp.gateway.auth.data.AuthRefreshRequest;
import hu.bme.jwtsampleapp.gateway.auth.data.AuthRequest;
import hu.bme.jwtsampleapp.gateway.auth.service.AuthService;
import hu.bme.jwtsampleapp.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(authService.authenticate(authRequest));
        } catch (AuthService.BadUsernameOrPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/logout")
    public ResponseEntity logout() {
        authService.logout(SecurityUtil.getUserId());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/refresh")
    public ResponseEntity refresh(@RequestBody AuthRefreshRequest refreshRequest) {
        try {
            return ResponseEntity.ok(authService.refresh(refreshRequest));
        } catch (AuthService.InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
