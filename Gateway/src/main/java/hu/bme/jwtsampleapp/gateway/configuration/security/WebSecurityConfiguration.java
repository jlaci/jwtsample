package hu.bme.jwtsampleapp.gateway.configuration.security;

import hu.bme.jwtsampleapp.entities.user.claims.UserClaimsMapper;
import hu.bme.jwtsampleapp.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserClaimsMapper userClaimsMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public WebSecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .authorizeRequests()

                // Allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()

                // Allow anonymous logins
                .antMatchers("/auth/**").permitAll()

                // Allow public apis
                .antMatchers("/public/**").permitAll()

                //Allow swagger and springfox
                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/v2/api-docs**", "/swagger**").permitAll()

                //Allow pre-flight cors
                .antMatchers(HttpMethod.OPTIONS).permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()

                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new JwtFilter(jwtSecret, userClaimsMapper), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

