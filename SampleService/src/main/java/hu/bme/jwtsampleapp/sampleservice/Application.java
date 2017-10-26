package hu.bme.jwtsampleapp.sampleservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "hu.bme.jwtsampleapp")
@EntityScan(basePackages = "hu.bme.jwtsampleapp")
@Slf4j
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("JWT Sample App Sample Service successfully started!");
    }
}