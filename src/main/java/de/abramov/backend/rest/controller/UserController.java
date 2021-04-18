package de.abramov.backend.rest.controller;

import de.abramov.backend.rest.dto.TokenDTO;
import de.abramov.backend.rest.dto.UserDataDTO;
import de.abramov.backend.rest.dto.UserResponseDTO;
import de.abramov.backend.rest.entity.User;
import de.abramov.backend.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "OK."),
      @ApiResponse(responseCode = "400", description = "Bad request."),
      @ApiResponse(
          responseCode = "401",
          description = "Authorization information is missing or invalid."),
    })
@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User Management Endpoints")
@ResponseBody
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired private UserService userService;
  private ModelMapper modelMapper = new ModelMapper();

  @PostMapping(
      value = "/signin",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenDTO> login(@RequestBody UserDataDTO user) {
    logger.info("Signin Request");
    Optional<String> optionalJwt = userService.signin(user.getUsername(), user.getPassword());
    if (optionalJwt.isPresent()) {
      return new ResponseEntity<>(new TokenDTO(optionalJwt.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new TokenDTO("UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping(
      value = "/signup",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenDTO> signup(@RequestBody UserDataDTO user) {

    Optional<String> optionalJWT = userService.signup(modelMapper.map(user, User.class));
    return optionalJWT
        .map(o -> new ResponseEntity<>(new TokenDTO(optionalJWT.get()), HttpStatus.OK))
        .orElse(new ResponseEntity<>(new TokenDTO(""), HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping(
      value = "/{username}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String delete(@PathVariable String username) {
    userService.delete(username);
    return username;
  }

  @Operation(security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping(
      value = "/{username}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public UserResponseDTO search(@PathVariable String username) {
    return modelMapper.map(userService.search(username), UserResponseDTO.class);
  }

  @Operation(
      summary = "Who Am I?",
      description = "Returns information about user.",
      security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }

  @Operation(security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public TokenDTO refresh(HttpServletRequest req) {
    return new TokenDTO(userService.refresh(req.getRemoteUser()));
  }
}
