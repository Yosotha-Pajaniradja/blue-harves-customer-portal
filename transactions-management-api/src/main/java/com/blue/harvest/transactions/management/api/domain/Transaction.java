package com.blue.harvest.transactions.management.api.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class Transaction {
  private BigDecimal creditAmount;
  private LocalDateTime transactionDate;
  private TransactionInfo transactionInfo;
}
