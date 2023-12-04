package com.blue.harvest.transactions.management.api.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Table(name = "TRANSACTIONS_CLIENT", schema = "TRANSACTION")
@Getter
@ToString
@Entity
@NoArgsConstructor
public class TransactionEntity extends BaseEntity implements Serializable {

  @Column(name = "TRANSACTION_IDENTIFIER", updatable = false, unique = true)
  @Setter(value = AccessLevel.PRIVATE)
  private String idTransaction;

  @Column(name = "account_number_source")
  @Setter(value = AccessLevel.PRIVATE)
  private String accountNumberSource;

  @Column(name = "account_number_target")
  @Setter(value = AccessLevel.PRIVATE)
  private String accountNumberTarget;

  @Column(name = "transaction_amount")
  @Setter(value = AccessLevel.PRIVATE)
  private BigDecimal transactionAmount;

  @Column(name = "customer_account_identifier")
  @Setter(value = AccessLevel.PRIVATE)
  private String customerAccountIdentifier;

  @Builder(setterPrefix = "with", builderMethodName = "transactionBuilder")
  private static TransactionEntity createTransactionEntity(@NonNull final String idTransaction,
      final BigDecimal transactionAmount, final String accountTransactionSource,
      final String accountTransactionTarget, final String customerAccountIdentifier) {
    final TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setIdTransaction(idTransaction);
    transactionEntity.setTransactionAmount(transactionAmount);
    transactionEntity.setAccountNumberSource(accountTransactionSource);
    transactionEntity.setAccountNumberTarget(accountTransactionTarget);
    transactionEntity.setCustomerAccountIdentifier(customerAccountIdentifier);
    transactionEntity.setCreationDate(LocalDateTime.now());
    return transactionEntity;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;

    final TransactionEntity transactionEntity = (TransactionEntity) o;
    return Objects.equals(id, transactionEntity);
  }

  @Override
  public int hashCode() {
    return 123445634;
  }
}
