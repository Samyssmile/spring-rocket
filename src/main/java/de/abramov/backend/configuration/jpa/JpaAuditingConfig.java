package de.abramov.backend.configuration.jpa;

import de.abramov.backend.configuration.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Data JPA is a framework that extends JPA by adding an extra layer of abstraction on the
 * top of the JPA provider. This layer allows for support for creating JPA repositories by extending
 * Spring JPA repository interfaces.
 * 
 * @author Samuel Abramov Oct 2, 2019
 *
 */

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }

}
