package com.blue.harvest.customer.management.api.infra.resource;

import com.blue.harvest.customer.management.api.infra.dto.CustomerCreationDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerResponseDto;
import com.blue.harvest.customer.management.api.infra.utils.SecurityProblemResponsesConfiguration;
import com.blue.harvest.customer.management.api.mapper.CustomerDtoMapper;
import com.blue.harvest.customer.management.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api/v1/customers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Customer Information Resource", description = "Customer information API")
@ApiResponse(responseCode = "500",
    ref = SecurityProblemResponsesConfiguration.INTERNAL_SERVER_ERROR_RESPONSE_REF)
@ApiResponse(responseCode = "401",
    ref = SecurityProblemResponsesConfiguration.UNAUTHORIZED_401_NO_TOKEN_RESPONSE_REF)
@ApiResponse(responseCode = "403",
    ref = SecurityProblemResponsesConfiguration.FORBIDDEN_403_RESPONSE_REF)
@SecurityRequirement(name = "keycloak", scopes = "role_consult_create")
@Validated
@Slf4j
public class CustomerResource {
  private final CustomerService customerService;
  private final CustomerDtoMapper customerDtoMapper;

  public CustomerResource(CustomerService customerService, CustomerDtoMapper customerDtoMapper) {
    this.customerService   = customerService;
    this.customerDtoMapper = customerDtoMapper;
  }

  @Operation(summary = "Create a new customer account",
      security = @SecurityRequirement(name = "keycloak",
          scopes = {"infra.blue-harvest-customer-info.v1"}))
  @ApiResponses({@ApiResponse(responseCode = "201", content = {
      @Content(schema = @Schema(implementation = CustomerResponseDto.class),
          mediaType = "application/json")})})
  @PostMapping(value = "/createAccount",
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  @PreAuthorize(
      "hasAuthority('SCOPE_api.blue-harvest-customer-info.v1') or hasRole('role_consult_create')")
  public ResponseEntity<CustomerResponseDto> createNewAccountForCustomer(
      @RequestBody CustomerCreationDto customerDetails) {
    try {
      CustomerResponseDto customerResponseDto =
          customerService.createCustomerAccount(customerDtoMapper.toDomain(customerDetails));
      return new ResponseEntity<>(CustomerResponseDto.builder()
          .withCustomerTransactionDto(customerResponseDto.getCustomerTransactionDto())
          .withMessage(customerResponseDto.getMessage()).build(), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Retrieve all Customer Info")
  @ApiResponses({@ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = CustomerDto.class),
          mediaType = "application/json")}),
      @ApiResponse(responseCode = "204", description = "There are no Customer Info",
          content = {@Content(schema = @Schema())}),})
  @GetMapping(value = "/get", produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  public ResponseEntity<List<CustomerDto>> getAllCustomerInfo() {
    try {
      List<CustomerDto> customerDto = customerDtoMapper.toDto(customerService.findAll());

      if (customerDto.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(customerDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Retrieve a Customer Data by Customer identifier",
      description = "Get a Customer Data object by specifying its id. The response is Customer Data object with id, title, description and published status.")
  @ApiResponses({@ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = CustomerDto.class),
          mediaType = "application/json")}),
      @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),})
  @GetMapping(value = "/get/{id}",
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  public ResponseEntity<List<CustomerDto>> getAccountDetailsOfCustomer(
      @PathVariable("id") String id) {
    List<CustomerDto> customerDto =
        customerDtoMapper.toDto(List.of(customerService.findByCustomerIdentifier(id)));
    if (customerDto != null) {
      return new ResponseEntity<>(customerDto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
