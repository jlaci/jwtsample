package hu.bme.jwtsampleapp.security.jwt;


import hu.bme.jwtsampleapp.entities.user.claims.UserClaimsMapper;
import hu.bme.jwtsampleapp.security.spring.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private String jwtSecret;

    private UserClaimsMapper userClaimsMapper;

    public JwtFilter(String jwtSecret, UserClaimsMapper userClaimsMapper) {
        this.jwtSecret = jwtSecret;
        this.userClaimsMapper = userClaimsMapper;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7); // The part after "Bearer "

            try {
                final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
                SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(userClaimsMapper.mapTo(claims)));
                chain.doFilter(req, res);
                SecurityContextHolder.getContext().setAuthentication(null);
            } catch (final SignatureException e) {
                error(res, "Invalid token!");
            } catch (final ExpiredJwtException e) {
                error(res, "Expired token!");
            }
        } else {
            chain.doFilter(req, res);
            SecurityContextHolder.getContext().setAuthentication(null);
        }


    }

    private void error(ServletResponse res, String msg) throws IOException, ServletException {
        if(res instanceof HttpServletResponse) {
            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);
        } else {
            throw new ServletException(msg);
        }
    }
}