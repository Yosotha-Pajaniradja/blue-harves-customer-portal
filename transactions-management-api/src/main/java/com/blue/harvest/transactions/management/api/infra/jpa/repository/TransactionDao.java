package com.blue.harvest.transactions.management.api.infra.jpa.repository;

import com.blue.harvest.transactions.management.api.domain.TransactionDomain;
import com.blue.harvest.transactions.management.api.infra.jpa.entity.TransactionEntity;
import com.blue.harvest.transactions.management.api.infra.jpa.mapper.TransactionEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionDao {

  private final TransactionRepository transactionRepository;

  private final TransactionEntityMapper transactionEntityMapper;

  public TransactionDao(TransactionRepository transactionRepository,
      TransactionEntityMapper transactionEntityMapper) {
    this.transactionRepository   = transactionRepository;
    this.transactionEntityMapper = transactionEntityMapper;
  }

  public TransactionEntity buildNewTransaction(final TransactionDomain transactionIdentifier) {
    return transactionRepository.save(
        TransactionEntity.transactionBuilder().withIdTransaction(UUID.randomUUID().toString())
            .withAccountTransactionTarget(transactionIdentifier.getAccountNumberTarget())
            .withAccountTransactionSource(transactionIdentifier.getAccountNumberSource())
            .withTransactionAmount(transactionIdentifier.getTransaction().getCreditAmount())
            .withCustomerAccountIdentifier(transactionIdentifier.getCustomerAccountIdentifier())
            .build());
  }

  public List<TransactionEntity> getTransactionInfo(final String customerIdentifier) {
    return transactionRepository.findTransactionEntitiesByCustomerAccountIdentifier(
        customerIdentifier);
  }

  public List<TransactionEntity> getAllTransactions() {
    return transactionRepository.findAll();
  }
}
