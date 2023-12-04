package com.blue.harvest.customer.management.api.infra.jpa.entity;

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
import java.time.LocalDateTime;
import java.util.Objects;


@Table(name = "CUSTOMER_INFO", schema = "CUSTOMER_KYC")
@Getter
@ToString
@Entity
@NoArgsConstructor
public class CustomerEntity extends BaseEntity implements Serializable {

  @Column(name = "CUSTOMER_IDENTIFIER", updatable = false, unique = true)
  @Setter(value = AccessLevel.PRIVATE)
  private String idCustomer;

  @Column(name = "FIRST_NAME")
  @Setter(value = AccessLevel.PRIVATE)
  private String firstName;

  @Column(name = "LAST_NAME")
  @Setter(value = AccessLevel.PRIVATE)
  private String lastName;

  @Column(name = "NO_OF_VALID_ACCOUNT")
  @Setter(value = AccessLevel.PRIVATE)
  private Integer noOfValidAccount;

  @Column(name = "COUNTRY")
  @Setter(value = AccessLevel.PRIVATE)
  private String country;

  @Column(name = "TAX_PROFILE")
  @Setter(value = AccessLevel.PRIVATE)
  private String taxProfile;


  @Column(name = "OPERATING_CURRENCY")
  @Setter(value = AccessLevel.PRIVATE)
  private String operatingCurrency;


  @Builder(setterPrefix = "with", builderMethodName = "customerBuilder")
  private static CustomerEntity createCustomerEntity(@NonNull final String idCustomer,
      final String firstName, final String lastName, final String country, final String taxProfile,
      final String operatingCurrency, final Integer noOfValidAccount) {
    final CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setIdCustomer(idCustomer);
    customerEntity.setCreationDate(LocalDateTime.now());
    customerEntity.setCountry(country);
    customerEntity.setFirstName(firstName);
    customerEntity.setLastName(lastName);
    customerEntity.setTaxProfile(taxProfile);
    customerEntity.setNoOfValidAccount(noOfValidAccount);
    customerEntity.setOperatingCurrency(operatingCurrency);
    return customerEntity;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;

    final CustomerEntity customerEntity = (CustomerEntity) o;
    return Objects.equals(id, customerEntity);
  }

  @Override
  public int hashCode() {
    return 123445634;
  }
}
