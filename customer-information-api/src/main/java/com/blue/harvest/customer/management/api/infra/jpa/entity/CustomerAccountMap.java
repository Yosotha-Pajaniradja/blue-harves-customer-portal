package com.blue.harvest.customer.management.api.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder(setterPrefix = "with")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CustomerAccountMap implements Serializable {
  @Column(name = "account_number", nullable = false) private String accountNumber;
  @Column(name = "CUSTOMER_IDENTIFIER", updatable = false) private String customerIdentifier;

}
