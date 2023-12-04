package com.blue.harvest.transactions.management.api.domain;

import com.blue.harvest.transactions.management.api.infra.dto.TransactionDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionInfo {
  List<TransactionDto> transactionDtoList;

  String message ;
}
