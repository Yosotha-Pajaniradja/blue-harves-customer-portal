package com.blue.harvest.customer.management.api.mapper;


import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.domain.Transaction;
import com.blue.harvest.customer.management.api.infra.dto.AccountsDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerCreationDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@NoArgsConstructor
public class CustomerDtoMapper {
  public List<CustomerDto> toDto(final List<CustomerDomain> customerDomain) {
    List<CustomerDto> list = new ArrayList<>();
    for (CustomerDomain customerDomain1 : customerDomain) {
      CustomerDto build =
          CustomerDto.builder().withCustomerIdentifier(customerDomain1.getIdCustomer())
              .withIdentifier(customerDomain1.getIdCustomer())
              .withCurrency(customerDomain1.getOperatingCurrency())
              .withLastName(customerDomain1.getFirstName())
              .withFirstName(customerDomain1.getLastName()).withListOfAccounts(
                  customerDomain1.getAccountsDomainList().stream().map(accountsDomain -> AccountsDto.builder().withAccountNumber(accountsDomain.getAccountNumber())
                      .withValidityDate(accountsDomain.getValidityDate())
                      .withCreationDate(accountsDomain.getCreationDate()).build()).collect(Collectors.toList())).build();

      list.add(build);
    }
    return list;
  }

  public CustomerDomain toDomain(final CustomerCreationDto customerDomain) {
    return CustomerDomain.builder().withIdCustomer(customerDomain.getCustomerIdentifier())
        .withFirstName(customerDomain.getFirstName()).withLastName(customerDomain.getLastName())
        .withTransaction(Transaction.builder().withTransactionDate(LocalDateTime.now()).build()).build();
  }
}
