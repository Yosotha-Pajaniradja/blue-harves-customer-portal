package com.blue.harvest.customer.management.api.service;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
public class TransactionResponse implements Serializable {

  private String accountNumberSource;

  private String accountNumberTarget;

  private BigDecimal amount;

  private String customerAccountIdentifier;

}
