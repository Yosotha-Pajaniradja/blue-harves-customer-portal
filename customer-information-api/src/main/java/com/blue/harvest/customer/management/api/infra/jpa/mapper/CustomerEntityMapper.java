package com.blue.harvest.customer.management.api.infra.jpa.mapper;

import com.blue.harvest.customer.management.api.domain.AccountsDomain;
import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.infra.dto.TaxProfile;
import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerEntity;
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
    return customerEntity.stream().map(
            customerEntity1 -> CustomerDomain.builder().withCountry(customerEntity1.getCountry())
                .withFirstName(customerEntity1.getFirstName())
                .withIdCustomer(customerEntity1.getIdCustomer())
                .withLastName(customerEntity1.getLastName())
                .withTaxProfile(String.valueOf(TaxProfile.valueOf(customerEntity1.getTaxProfile())))
                .withNoOfValidAccounts(customerEntity1.getNoOfValidAccount()).withAccountsDomainList(
                    customerEntity1.getAccountEntities().stream().map(
                            customerAccountEntity -> AccountsDomain.builder()
                                .withAccountNumber(customerAccountEntity.getAccountNumber())
                                .withValidityDate(customerAccountEntity.getValidityDate())
                                .withCreationDate(customerAccountEntity.getCreationDate()).build())
                        .collect(Collectors.toList()))
                .withOperatingCurrency(customerEntity1.getOperatingCurrency()).build())
        .collect(Collectors.toList());
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
