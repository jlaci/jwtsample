package hu.bme.jwtsampleapp.gateway.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost").allowedMethods("GET", "POST", "PUT", "DELETE");
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT", "DELETE");
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE");
                registry.addMapping("/**").allowedOrigins("http://localhost:8000").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

}
