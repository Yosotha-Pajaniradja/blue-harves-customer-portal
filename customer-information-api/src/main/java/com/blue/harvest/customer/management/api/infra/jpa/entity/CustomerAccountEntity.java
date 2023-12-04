package com.blue.harvest.customer.management.api.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Table(name = "CUSTOMER_ACCOUNT", schema = "CUSTOMER_KYC")
@Getter
@ToString
@Entity
@NoArgsConstructor
public class CustomerAccountEntity implements Serializable {

  @Column(name = "CUSTOMER_IDENTIFIER")
  @Setter(value = AccessLevel.PRIVATE)
  private String identifierCustomer;

  @Id
  @Column(name = "account_number", updatable = false, unique = true)
  @Setter(value = AccessLevel.PRIVATE)
  private String accountNumber;


  @Column(name = "validity_date")
  @Setter(value = AccessLevel.PRIVATE)
  private LocalDateTime validityDate;


  @Column(name = "CREATION_DATE", nullable = false)
  @Setter(value = AccessLevel.PRIVATE)
  private LocalDateTime creationDate;


  @Builder(setterPrefix = "with", builderMethodName = "customerAccountBuilder")
  private static CustomerAccountEntity createCustomerAccountEntity(@NonNull final String idCustomer,
      final String accountNumber, final LocalDateTime validityDate,
      final LocalDateTime creationDate) {
    final CustomerAccountEntity customerEntity = new CustomerAccountEntity();
    customerEntity.setIdentifierCustomer(idCustomer);
    customerEntity.setAccountNumber(accountNumber);
    customerEntity.setValidityDate(validityDate);
    customerEntity.setCreationDate(creationDate);

    return customerEntity;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;

    final CustomerAccountEntity customerEntity = (CustomerAccountEntity) o;
    return Objects.equals(id, customerEntity);
  }

  @Override
  public int hashCode() {
    return 1234445634;
  }
}
