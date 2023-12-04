package com.blue.harvest.transactions.management.api.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;



@Builder(setterPrefix = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TransactionDomain implements Serializable{

  private String idTransaction;

  private Transaction transaction;

  private String accountNumberSource;

  private String accountNumberTarget;

  private String customerAccountIdentifier;

}
