package com.blue.harvest.customer.management.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
@SecurityScheme(scheme = "bearer", name = "keycloak", type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER, bearerFormat = "JWT",
    description = "JWT authorization by keykloak")
@SecurityRequirement(name = "keycloak")
public class OpenAPIConfig {

  @Value("${openapi.dev-url}") private String devUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Contact contact = new Contact();
    contact.setEmail("mock@blueharvest.com");
    contact.setName("blueharvest");

    License mitLicense =
        new License().name("BlueHarvest License").url("https://blueharvest.com/licenses/");

    Info info = new Info().title("API to manage customer accounts").version("1.0").contact(contact)
        .description("This API expose customer management endpoints.")
        .termsOfService("https://www.blueharvest.com/terms").license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }


}
