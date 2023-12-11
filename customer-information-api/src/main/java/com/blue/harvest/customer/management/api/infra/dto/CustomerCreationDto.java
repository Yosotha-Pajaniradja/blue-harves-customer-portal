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
import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Instruction", description = "Rerpresents the customer referential info")
public class CustomerCreationDto implements Serializable {

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

  @Schema(description = "Currency of transactions")
  @Size(max = 3)
  private String Currency;

  private BigDecimal creditAmount;

}
