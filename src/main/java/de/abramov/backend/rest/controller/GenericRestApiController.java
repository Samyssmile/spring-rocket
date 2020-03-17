package de.abramov.backend.rest.controller;

import de.abramov.backend.rest.entity.IEntity;
import de.abramov.backend.rest.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "OK."),
      @ApiResponse(responseCode = "201", description = "Element created."),
      @ApiResponse(responseCode = "400", description = "Bad request."),
      @ApiResponse(
          responseCode = "401",
          description = "Authorization information is missing or invalid."),
      @ApiResponse(responseCode = "404", description = "Element not found.")
    })
public class GenericRestApiController<
    E extends IEntity, R extends JpaRepository<E, Integer>, S extends GenericService<E, R>> {
  private Logger logger = LoggerFactory.getLogger(GenericRestApiController.class);

  @Autowired private S service;

  @Operation(summary = "Add a new element", description = "Create a new element and save it")
  @PostMapping(
      value = "/element/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public E createElement(@RequestBody E item) {
    logger.info("Save element {}", item);
    return service.save(item);
  }

  @Operation(
      summary = "Count all elements",
      description = "Count all elements and return the result.")
  @GetMapping(value = "/element/count", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public long countElements() {
    logger.info("Count elements");
    return service.count();
  }

  @Operation(
      summary = "Find element by ID",
      description = "Search and Return an element by given ID.")
  @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Element not found")})
  @GetMapping(value = "/element/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public E findElementById(@PathVariable("id") int id) {
    logger.info("Find element by ID: {}", id);
    return service.findElementById(id).map(f -> f).orElse(null);
  }

  @Operation(
      summary = "Find all",
      description = "Collect all elements and return them as a JSON SET.")
  @GetMapping(value = "/element/", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Set<E> findAll() {
    logger.info("Find all");
    return service.findAll();
  }

  /**
   * Delete Element
   *
   * @param id ID of the element
   * @return HTTPStatus
   */
  @Operation(summary = "Delete By Id", description = "Find and delete element by ID.")
  @DeleteMapping(value = "/element/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> removeElementById(
      @Parameter(description = "Id of the element to be removed. Cannot be empty.", required = true)
          @PathVariable("id")
          int id) {
    logger.info("Fetching & Deleting element with id {}", id);

    Optional<E> foundElement = service.findElementById(id);
    if (foundElement.isPresent()) {
      service.removeElementById(id);
    } else {
      logger.error("Unable to delete. Element with id {} not found.", id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Delete all", description = "Find and delete all elements.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Delete All - Successfull "),
      })
  @DeleteMapping(value = "/element/")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    logger.info("Deleting All elements");

    service.deleteAll();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(summary = "Find Page", description = "Find and Return requested page.")
  @GetMapping(path = "/element/page", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<E> findPage(Pageable pageable) {
    return service.findPage(pageable);
  }

  @Operation(
      summary = "Find all elements.",
      description =
          "Find all elements in a pagination context. Return all elements of requested page.")
  @GetMapping(path = "/element/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<E>> getAllElements(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    List<E> list = service.findAllElements(pageNo, pageSize, sortBy);

    return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
  }

  @Operation(
      summary = "Update an existing element",
      description = "Find and update an exsiting element.")
  @PutMapping("/{id}")
  public ResponseEntity<E> update(
      @Parameter(description = "Id of the element to be update. Cannot be empty.", required = true)
          @PathVariable
          int id,
      @Valid @RequestBody E element) {
    return ResponseEntity.ok(service.updateElement(element));
  }

  public S getService() {
    return service;
  }
}
