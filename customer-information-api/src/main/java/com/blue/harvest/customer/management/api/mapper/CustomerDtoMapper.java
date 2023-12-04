package com.blue.harvest.customer.management.api.mapper;


import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.domain.Transaction;
import com.blue.harvest.customer.management.api.infra.dto.CustomerDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
@NoArgsConstructor
public class CustomerDtoMapper {
  public List<CustomerDto> toDto(final List<CustomerDomain> customerDomain) {
    return customerDomain.stream().map(customerDomain1 -> CustomerDto.builder().withCustomerIdentifier(customerDomain1.getIdCustomer())
        .withIdentifier(customerDomain1.getIdCustomer())
        .withCurrency(customerDomain1.getOperatingCurrency())
        .withLastName(customerDomain1.getFirstName()).withLastName(customerDomain1.getLastName())
        .build()).collect(Collectors.toList());
  }

  public CustomerDomain toDomain(final CustomerDto customerDomain) {
    return CustomerDomain.builder().withIdCustomer(customerDomain.getCustomerIdentifier())
        .withFirstName(customerDomain.getFirstName()).withLastName(customerDomain.getLastName())
        .withTransaction(Transaction.builder().withTransactionDate(LocalDateTime.now())
            .withCreditAmount(customerDomain.getInitialCredit()).build()).build();
  }
}
