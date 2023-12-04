package com.blue.harvest.customer.management.api.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Builder(setterPrefix = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CustomerDomain implements Serializable{

  private String idCustomer;

  private String firstName;

  private String lastName;

  private String country;

  private Integer noOfValidAccounts;

  private String taxProfile;

  private String OperatingCurrency;

  private Transaction transaction;


}
