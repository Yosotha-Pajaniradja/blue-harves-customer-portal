package com.blue.harvest.transactions.management.api.infra.resource;

import com.blue.harvest.transactions.management.api.infra.dto.TransactionCreationResponseDto;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionDto;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionResponseDto;
import com.blue.harvest.transactions.management.api.infra.utils.SecurityProblemResponsesConfiguration;
import com.blue.harvest.transactions.management.api.mapper.TransactionDtoMapper;
import com.blue.harvest.transactions.management.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RestController
@ControllerAdvice
@RequestMapping(value = "/api/v1/transactions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Transaction Management Resource", description = "Transaction Management API")
@ApiResponse(responseCode = "500",
    ref = SecurityProblemResponsesConfiguration.INTERNAL_SERVER_ERROR_RESPONSE_REF)
@ApiResponse(responseCode = "401",
    ref = SecurityProblemResponsesConfiguration.UNAUTHORIZED_401_NO_TOKEN_RESPONSE_REF)
@ApiResponse(responseCode = "403",
    ref = SecurityProblemResponsesConfiguration.FORBIDDEN_403_RESPONSE_REF)
//@SecurityRequirement(name = "keycloak", scopes = "role_consult_create")
@Validated
@Slf4j
public class TransactionResource {
  private final TransactionService transactionService;
  private final TransactionDtoMapper transactionDtoMapper;

  public TransactionResource(TransactionService transactionService,
      TransactionDtoMapper transactionDtoMapper) {
    this.transactionService   = transactionService;
    this.transactionDtoMapper = transactionDtoMapper;
  }

  @Operation(summary = "Create a new transaction",
      security = @SecurityRequirement(name = "keycloak",
          scopes = {"api.blue-harvest-transaction-management.v1",
              "api.blue-harvest-transaction-management-create.v1"}))
  @ApiResponse(responseCode = "201", content = {
      @Content(schema = @Schema(implementation = TransactionResponseDto.class),
          mediaType = "application/json")})
  @PostMapping(value = "/createNewTransaction",
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
//  @PreAuthorize(
//      "hasAuthority('SCOPE_api.blue-harvest-transaction-management.v1') and hasAuthority('SCOPE_api.blue-harvest-transaction-management-create.v1') or hasRole('role_consult_create')")
  public ResponseEntity<TransactionCreationResponseDto> createNewTransaction(
      @RequestBody TransactionDto transactionDetails) {
    try {
      TransactionCreationResponseDto transactionInfo = transactionService.createNewTransaction(
          transactionDtoMapper.toDomain(transactionDetails));
      return new ResponseEntity<>(transactionInfo, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Retrieve all Transaction information of the user",
      security = @SecurityRequirement(name = "keycloak",
          scopes = {"api.blue-harvest-transaction-management.v1",
              "api.blue-harvest-transaction-management-consult.v1"}))
  @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = TransactionResponseDto.class),
          mediaType = "application/json")})
  @GetMapping(value = "/get/{customerIdentifier}",
      produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  @PreAuthorize(
      "hasAuthority('SCOPE_api.blue-harvest-transaction-management.v1') and hasAuthority('SCOPE_api.blue-harvest-transaction-management-consult.v1')")

  public ResponseEntity<TransactionResponseDto> getAllTransactionDetails(
      @PathVariable String customerIdentifier) {
    try {
      TransactionResponseDto transactionDto =
          transactionService.findByTransactionIdentifier(customerIdentifier);
      return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Retrieve a all Transaction",
      description = "Get a Transaction Data object by specifying its id. The response is Transaction Data object with id, title, description and published status.",
      security = @SecurityRequirement(name = "keycloak",
          scopes = {"api.blue-harvest-transaction-management.v1",
              "api.blue-harvest-transaction-management-consult.v1"}))
  @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = TransactionResponseDto.class),
          mediaType = "application/json")})
  @GetMapping(value = "/get", produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
  @PreAuthorize(
      "hasAuthority('SCOPE_api.blue-harvest-transaction-management.v1') and hasAuthority('SCOPE_api.blue-harvest-transaction-management-consult.v1')")
  public ResponseEntity<TransactionResponseDto> getAllTransactions() {
    try {
      TransactionResponseDto transactionDto = transactionService.findAll();
      return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
