package com.blue.harvest.customer.management.api.infra.dto;

import com.blue.harvest.customer.management.api.infra.utils.SafeString;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Instruction", description = "Rerpresents the customer referential info")
public class CustomerTransactionDto implements Serializable {

  @Hidden private String Identifier;

  @Schema(description = "Unique Customer Identifier")
  @Size(max = 15)
  @SafeString
  private String customerIdentifier;

  @Schema(description = "First Name of the Customer")
  @Size(min = 6, max = 15)
  @SafeString
  private String firstName;

  @Schema(description = "Last name of the customer")
  @Size(min = 6, max = 15)
  @SafeString
  private String lastName;

  @Schema(description = "Total no of valid customer that the customer is bearing",
      example = "344.232", format = "decimal", multipleOf = 0.0000000000001) private BigDecimal
      initialCredit;

  @Schema(description = "the new account number of the transactions")
  @Size(max = 3)
  private String Currency;
  @Schema(description = "The newly created account number", required = true)
  @Size(min = 10, max = 50)
  @SafeString
  private String newAccountNumber;


}
