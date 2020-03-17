package de.abramov.backend.configuration.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    Info info = new Info()
        .title("Spring Boot Acceleration Project")
        .description(
            "This is a basic spring boot project with mostly all important features integrated and activated. - DRY Motivation")
        .version("1.0");

    info.setContact(new Contact().name("Samuel Abramov").email("info@abramov-samuel.de").url("http://www.abramov-samuel.de"));

    return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).info(info);
  }



}
