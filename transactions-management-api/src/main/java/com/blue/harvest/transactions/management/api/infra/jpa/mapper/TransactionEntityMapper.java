package com.blue.harvest.transactions.management.api.infra.jpa.mapper;

import com.blue.harvest.transactions.management.api.domain.Transaction;
import com.blue.harvest.transactions.management.api.domain.TransactionDomain;
import org.springframework.stereotype.Component;

import com.blue.harvest.transactions.management.api.infra.dto.TaxProfile;
import com.blue.harvest.transactions.management.api.infra.jpa.entity.TransactionEntity;

@Component
public class TransactionEntityMapper {
  public TransactionDomain toDomain(final TransactionEntity transactionEntity) {
    return TransactionDomain.builder().withIdTransaction(transactionEntity.getIdTransaction())
        .withTransaction(
            Transaction.builder().withTransactionDate(transactionEntity.getCreationDate())
                .withCreditAmount(transactionEntity.getTransactionAmount()).build())
        .withAccountNumberSource(transactionEntity.getAccountNumberSource())
        .withAccountNumberTarget(transactionEntity.getAccountNumberTarget())

        .build();
  }

  public TransactionEntity toEntity(final TransactionDomain transactionDomain) {
    return TransactionEntity.transactionBuilder()
        .withIdTransaction(transactionDomain.getIdTransaction())
        .withAccountTransactionSource(transactionDomain.getAccountNumberSource())
        .withTransactionAmount(transactionDomain.getTransaction().getCreditAmount())
        .withCustomerAccountIdentifier(transactionDomain.getCustomerAccountIdentifier())
        .withAccountTransactionTarget(transactionDomain.getAccountNumberTarget()).build();

  }
}
