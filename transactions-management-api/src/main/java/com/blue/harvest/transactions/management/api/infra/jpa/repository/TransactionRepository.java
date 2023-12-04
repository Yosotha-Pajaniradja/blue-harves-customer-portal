package com.blue.harvest.transactions.management.api.infra.jpa.repository;



import com.blue.harvest.transactions.management.api.domain.TransactionDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.harvest.transactions.management.api.infra.jpa.entity.TransactionEntity;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, TransactionDomain> {
  public TransactionEntity findTransactionEntitiesByIdTransaction(final String idTransaction);

  public List<TransactionEntity> findTransactionEntitiesByCustomerAccountIdentifier(
      final String customerAccountIdentifier);

}
