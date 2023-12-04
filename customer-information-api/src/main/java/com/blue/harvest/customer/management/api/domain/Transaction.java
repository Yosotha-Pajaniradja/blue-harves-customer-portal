package com.blue.harvest.customer.management.api.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class Transaction {
  private BigDecimal creditAmount;
  private LocalDateTime transactionDate;
  private AccountInfo accountInfo;
  private String accountIdentifier;
}
