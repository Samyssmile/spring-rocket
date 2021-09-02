package de.abramov.backend.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Autowired private JwtTokenProvider jwtTokenProvider;
  @Autowired private Environment environment;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Disable CSRF (cross site request forgery)
    http.csrf().disable();

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Entry points

    http.authorizeRequests() //
        .antMatchers("/users/signin")
        .permitAll() //
        .antMatchers("/users/signup")
        .permitAll() //
        // Disallow everything else..
        .anyRequest()
        .authenticated();

    // If a user try to access a resource without having enough permissions
    http.exceptionHandling().accessDeniedPage("/login");

    // Apply JWT
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    // Optional, if you want to test the API from a browser
    http.httpBasic();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {

    if (Arrays.asList(environment.getActiveProfiles()).contains("development")) {
      logger.info("*** Development Profile is active - JWT API Protection is disabled ***");

      // Allow swagger to be accessed without authentication
      web.ignoring()
          .antMatchers("/v3/api-docs") //
          .antMatchers("/swagger-resources/**") //
          .antMatchers("/swagger-ui.html") //
          .antMatchers("/api/swagger-ui.html") //
          .antMatchers("/configuration/**") //
          .antMatchers("/public/**")
          .antMatchers("/api/swagger-ui/*")
          // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in
          // production)
          .and()
          .ignoring()
          .antMatchers("/h2-console/**/**")
          .antMatchers("/api/swagger-ui/*")
          .antMatchers("/actuator/**")
          .antMatchers("/v1/api/**")
          .antMatchers("/users/me");
    }
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
