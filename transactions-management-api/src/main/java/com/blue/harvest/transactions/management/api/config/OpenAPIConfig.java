package com.blue.harvest.transactions.management.api.config;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition
@SecurityScheme(scheme = "bearer", name = "keycloak", type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER, bearerFormat = "JWT",
    description = "JWT authorization by keyCloak")
@SecurityRequirement(name = "keycloak")
public class OpenAPIConfig {

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl("http://localhost:8086");
    devServer.setDescription("Server URL in Development environment");
    Contact contact = new Contact();
    contact.setEmail("userQuery@blueharvest.com");
    contact.setName("UserBlueHarvest");

    License mitLicense =
        new License().name("blueHarvest License").url("https://blueharvest.com/licenses/");

    Info info = new Info().title("API to manage transactions").version("1.0").contact(contact)
        .description("This API expose transaction management endpoints.")
        .termsOfService("https://blueharvest.com/licenses/").license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }


}
