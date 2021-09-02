package de.abramov.backend.configuration.openapi;

import de.abramov.backend.configuration.i18n.I18n;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI(I18n i18n) {
        Info info = new Info()
            .title(i18n.getLocaleString("applications_name", Locale.ENGLISH))
            .description(
                i18n.getLocaleString("openapi_description", Locale.ENGLISH))
            .version(i18n.getLocaleString("openapi_version", Locale.ENGLISH));

        info.setContact(
            new Contact().name("Samuel Abramov").email("info@abramov-samuel.de").url("http://www.abramov-samuel.de"));

        return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
                                                                            new SecurityScheme()
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer").bearerFormat("JWT")))
                            .info(info);
    }


}
