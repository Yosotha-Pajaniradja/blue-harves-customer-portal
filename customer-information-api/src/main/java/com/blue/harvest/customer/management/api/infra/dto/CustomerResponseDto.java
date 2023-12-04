package com.blue.harvest.customer.management.api.infra.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CustomerResponseDto", description = "Represents the customer referential info")
public class CustomerResponseDto implements Serializable {

 private CustomerTransactionDto customerTransactionDto;
 private String message ;

}
