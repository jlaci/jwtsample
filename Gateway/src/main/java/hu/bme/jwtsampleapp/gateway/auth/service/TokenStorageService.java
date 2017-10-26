package hu.bme.jwtsampleapp.gateway.auth.service;

import hu.bme.jwtsampleapp.gateway.auth.data.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: In-memory token store for demonstration purposes, replace in production!
 */
@Service
@Slf4j
public class TokenStorageService {

    private Map<Long, RefreshToken> tokenStore = new HashMap<>();

    public void invalidateRefreshToken(Long userId) {
        log.debug("Invalidating refresh token for user {}", userId);
        tokenStore.remove(userId);
    }

    public void storeRefreshToken(Long id, String refreshToken, Date refreshTokenExpireDate) {
        log.debug("Storing refresh token for user {} with expiration date of {}", id, refreshTokenExpireDate);
        tokenStore.put(id, new RefreshToken(refreshToken, refreshTokenExpireDate));
    }

    public RefreshToken getRefreshToken(Long userId) {
        return tokenStore.get(userId);
    }
}
