package com.blue.harvest.transactions.management.api.mapper;


import com.blue.harvest.transactions.management.api.domain.Transaction;
import com.blue.harvest.transactions.management.api.domain.TransactionDomain;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionDto;
import com.blue.harvest.transactions.management.api.infra.dto.TransactionInfo;
import com.blue.harvest.transactions.management.api.infra.jpa.entity.TransactionEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@NoArgsConstructor
public class TransactionDtoMapper {
  public TransactionDto toDto(final TransactionEntity transactionDomain) {
    return TransactionDto.builder()
        .withAccountNumberSource(transactionDomain.getAccountNumberSource())
        .withAccountNumberTarget(transactionDomain.getAccountNumberTarget())
        .withCustomerAccountIdentifier(transactionDomain.getCustomerAccountIdentifier())
        .withInitialCredit(transactionDomain.getTransactionAmount()).build();
  }

  public TransactionInfo toDtoTransactionInfo(final TransactionEntity transactionDomain) {
    return TransactionInfo.builder()
        .withAccountNumberSource(transactionDomain.getAccountNumberSource())
        .withAccountNumberTarget(transactionDomain.getAccountNumberTarget())
        .withCustomerAccountIdentifier(transactionDomain.getCustomerAccountIdentifier())
        .withInitialCredit(transactionDomain.getTransactionAmount())
        .withTransactionCreationDate(LocalDate.from(transactionDomain.getCreationDate())).build();
  }

  public TransactionDomain toDomain(final TransactionDto transactionDto) {
    return TransactionDomain.builder()
        .withAccountNumberSource(transactionDto.getAccountNumberSource())
        .withCustomerAccountIdentifier(transactionDto.getCustomerAccountIdentifier())
        .withAccountNumberTarget(transactionDto.getAccountNumberTarget()).withTransaction(
            Transaction.builder().withCreditAmount(transactionDto.getInitialCredit()).build())
        .build();
  }
}
