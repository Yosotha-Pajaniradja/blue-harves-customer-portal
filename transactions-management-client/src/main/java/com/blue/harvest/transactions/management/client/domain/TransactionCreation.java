package com.blue.harvest.transactions.management.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class TransactionCreation {

  private String accountNumberSource;

  private String accountNumberTarget;

  private String customerAccountIdentifier;

  private BigDecimal initialCredit;
}


