package de.abramov.backend.configuration.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Rest Api access configuration.
 * 
 * @author Samuel Abramov Oct 2, 2019
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("POST", "PUT", "DELETE", "GET", "OPTIONS", "PATCH")
        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
        .allowCredentials(false).maxAge(3600);
  }


  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
    registry.addRedirectViewController("/api/swagger-resources/configuration/ui",
        "/swagger-resources/configuration/ui");
    registry.addRedirectViewController("/api/swagger-resources/configuration/security",
        "/swagger-resources/configuration/security");
    registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/api/swagger-ui.html**")
        .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
    registry.addResourceHandler("/api/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }



}
