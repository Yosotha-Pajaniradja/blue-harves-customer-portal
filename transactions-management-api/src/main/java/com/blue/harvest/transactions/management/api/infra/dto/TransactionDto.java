package com.blue.harvest.transactions.management.api.infra.dto;

import com.blue.harvest.transactions.management.api.infra.utils.SafeString;
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
@Schema(name = "Transaction", description = "Represents the new Transaction creation")
public class TransactionDto implements Serializable {

  @Schema(description = "First Name of the Transaction", required = true)
  @Size(min = 10, max = 50)
  @SafeString
  private String accountNumberSource;

  @Schema(description = "First Name of the Transaction", required = true)
  @Size(min = 10, max = 50)
  @SafeString
  private String accountNumberTarget;

  @Schema(description = "First Name of the Transaction", required = true)
  @Size(min = 10, max = 50)
  @SafeString
  private String customerAccountIdentifier;

  @Schema(description = "Total no of valid transaction that the transaction is bearing",
      example = "344.232", format = "decimal", multipleOf = 0.0000000000001) private BigDecimal
      initialCredit;

}
