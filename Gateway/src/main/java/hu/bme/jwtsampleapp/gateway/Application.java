package hu.bme.jwtsampleapp.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "hu.bme.jwtsampleapp")
@EntityScan(basePackages = "hu.bme.jwtsampleapp")
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("JWT Sample App Gateway successfully started!");
    }
}
