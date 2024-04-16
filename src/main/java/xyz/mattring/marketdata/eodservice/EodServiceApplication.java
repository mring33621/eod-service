package xyz.mattring.marketdata.eodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO: figure out out to get GraphQL to work with Spring Security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class EodServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EodServiceApplication.class, args);
    }

}
