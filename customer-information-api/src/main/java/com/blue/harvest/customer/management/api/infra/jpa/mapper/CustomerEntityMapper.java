package com.blue.harvest.customer.management.api.infra.jpa.mapper;

import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerEntity;
import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.infra.dto.TaxProfile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerEntityMapper {
  public CustomerDomain toDomain(final CustomerEntity customerEntity) {
    return CustomerDomain.builder().withCountry(customerEntity.getCountry())
        .withFirstName(customerEntity.getFirstName()).withIdCustomer(customerEntity.getIdCustomer())
        .withLastName(customerEntity.getLastName())
        .withTaxProfile(String.valueOf(TaxProfile.valueOf(customerEntity.getTaxProfile())))
        .withNoOfValidAccounts(customerEntity.getNoOfValidAccount())
        .withFirstName(customerEntity.getFirstName()).build();
  }

  public List<CustomerDomain> toDomain(final List<CustomerEntity> customerEntity) {
    customerEntity.stream().map(customerEntity1 -> {
      return CustomerDomain.builder().withCountry(customerEntity1.getCountry())
          .withFirstName(customerEntity1.getFirstName()).withIdCustomer(customerEntity1.getIdCustomer())
          .withLastName(customerEntity1.getLastName())
          .withTaxProfile(String.valueOf(TaxProfile.valueOf(customerEntity1.getTaxProfile())))
          .withNoOfValidAccounts(customerEntity1.getNoOfValidAccount())
          .withFirstName(customerEntity1.getFirstName()).build();
    }).collect(Collectors.toList());
    return null;
  }

  public CustomerEntity toEntity(final CustomerDomain customerDomain) {
    return CustomerEntity.customerBuilder().withIdCustomer(customerDomain.getIdCustomer())
        .withCountry(customerDomain.getCountry()).withFirstName(customerDomain.getFirstName())
        .withLastName(customerDomain.getLastName())
        .withOperatingCurrency(customerDomain.getOperatingCurrency())
        .withTaxProfile(customerDomain.getTaxProfile().toString())
        .withNoOfValidAccount(customerDomain.getNoOfValidAccounts()).build();
  }
}
