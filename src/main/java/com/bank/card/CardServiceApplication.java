package com.bank.card;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Card microservice REST API Documentation", description = "EasyBank Cards microservice REST API Documentation", version = "v1", contact = @Contact(name = "Siddhant A", email = "siddhantawathare6@gmail.com", url = "https://github.com/siddhantawathare6-gif"), license = @License(name = "Apache 2.0", url = "https://github.com/siddhantawathare6-gif")), externalDocs = @ExternalDocumentation(description = "EasyBank Card microservice REST API Documentation", url = "https://github.com/siddhantawathare6-gif"))
public class CardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardServiceApplication.class, args);
    }

}
