package de.abramov.backend.rest.service;

import de.abramov.backend.configuration.security.JwtTokenProvider;
import de.abramov.backend.exception.CustomException;
import de.abramov.backend.rest.entity.User;
import de.abramov.backend.rest.repository.UserRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private AuthenticationManager authenticationManager;

  public Optional<String> signin(String username, String password) {
    Optional<String> optionalJWT = Optional.empty();
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      optionalJWT =
          Optional.of(
              jwtTokenProvider.createToken(
                  username, userRepository.findByUsername(username).getRoles()));
    } catch (AuthenticationException e) {
      logger.info("Failed login attempt", e.getCause());
    }

    return optionalJWT;
  }

  public Optional<String> signup(User user) {
    Optional<String> optionalJWT = Optional.empty();

    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      optionalJWT = Optional.of(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }

    return optionalJWT;
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  public User search(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUsername(
        jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(
        username, userRepository.findByUsername(username).getRoles());
  }
}
