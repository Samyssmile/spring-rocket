package de.abramov.backend.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.abramov.backend.rest.entity.DummyEntity;
import de.abramov.backend.rest.service.DummyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** Use this as your blueprint to create your Rest API Controller. */
@RestController
@RequestMapping("/v1/api/dummy")
@Tag(name = "Dummy", description = "Some Dummy Operations")
public class DummyController {
  @Autowired private DummyService dummyService;

  @Operation(
      summary = "Find all dummies",
      description = "Find all dummy elements and returm them.",
      tags = {"Dummy"},
      security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = DummyEntity.class))))
      })
  @GetMapping(
      value = "/dummies",
      produces = {"application/json", "application/xml"})
  @ResponseBody
  public ResponseEntity<Set<DummyEntity>> findAll() {
    return new ResponseEntity<>(dummyService.findAll(), HttpStatus.OK);
  }

  @Operation(
      summary = "Find dummy by ID",
      description = "Returns a single dummy",
      tags = {"Dummy"},
      security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(schema = @Schema(implementation = DummyEntity.class))),
        @ApiResponse(responseCode = "404", description = "Dummy not found")
      })
  @GetMapping(
      value = "/{id}",
      produces = {"application/json", "application/xml"})
  public ResponseEntity<DummyEntity> findDummyById(
      @Parameter(description = "Id of the dummy to be obtained. Cannot be empty.", required = true)
          @PathVariable
          int id) {
    Optional<DummyEntity> result = dummyService.findElementById(id);

    if (result.isPresent()) {
      // Hypermedia-Driven HATEOAS Example
      result.get().add(linkTo(methodOn(DummyController.class).findDummyById(id)).withSelfRel());
      return new ResponseEntity<DummyEntity>(result.get(), new HttpHeaders(), HttpStatus.OK);
    } else {
      return new ResponseEntity<DummyEntity>(new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(
      summary = "Add a new Dummy",
      description = "",
      tags = {"Dummy"},
      security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Dummy created",
            content = @Content(schema = @Schema(implementation = DummyEntity.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Dummy already exists")
      })
  @PostMapping(consumes = {"application/json", "application/xml"})
  public ResponseEntity<DummyEntity> addDummyElement(
      @Parameter(
              description = "Dummy to add. Cannot null or empty.",
              required = true,
              schema = @Schema(implementation = DummyEntity.class))
          @Valid
          @RequestBody
          DummyEntity element)
      throws URISyntaxException {

    return new ResponseEntity<DummyEntity>(
        dummyService.save(element), new HttpHeaders(), HttpStatus.OK);
  }

  @Operation(
      summary = "Add 10.000 Dummy Elements",
      description = "You can use this for checking Cache functions.",
      tags = {"Dummy"},
      security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping(value = "/generator")
  public ResponseEntity<HttpStatus> add10000Elements() throws URISyntaxException {
    Random generator = new Random();
    List<DummyEntity> listToAdd = new ArrayList<>();

    for (int i = 0; i < 10000; i++) {
      listToAdd.add(
          new DummyEntity(
              generator.nextInt(1000000), "Some Text Counter Dummy Text: " + i, "132165443"));
    }
    dummyService.saveAll(listToAdd);
    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
  }
}
