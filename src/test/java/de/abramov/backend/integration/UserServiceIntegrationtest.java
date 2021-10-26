package de.abramov.backend.integration;

import de.abramov.backend.rest.controller.UserController;
import de.abramov.backend.rest.dto.TokenDTO;
import de.abramov.backend.rest.dto.UserDataDTO;
import de.abramov.backend.rest.entity.Role;
import de.abramov.backend.rest.service.AuthenticationService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationtest {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserController userController;

    @Test
    void testSignUpAsAdmin(){
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.ROLE_ADMIN);

        UserDataDTO dummyUserDTO = new UserDataDTO();
        dummyUserDTO.setPassword("123456");
        dummyUserDTO.setEmail("dummy@mail.de");
        dummyUserDTO.setUsername("Jon Snow");
        dummyUserDTO.setRoles(roleList);

        ResponseEntity<TokenDTO> signUpResponse = userController.signup(dummyUserDTO);
        assertEquals(HttpStatus.OK, signUpResponse.getStatusCode());
//        responseentity<tokendto> response = usercontroller.login(dummyuserdto);
//
//        System.out.println(response.getStatusCode());

    }
}
