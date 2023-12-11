package com.blue.harvest.customer.management.api.infra.jpa.repository;

import com.blue.harvest.customer.management.api.domain.AccountInfo;
import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerAccountEntity;
import com.blue.harvest.customer.management.api.infra.jpa.mapper.CustomerEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDao {

  private final CustomerRepository customerRepository;

  private final CustomerAccountRepository customerAccountRepository;

  private final CustomerEntityMapper customerEntityMapper;

  public CustomerDao(CustomerRepository customerRepository,
      CustomerAccountRepository customerAccountRepository, CustomerEntityMapper customerEntityMapper) {
    this.customerRepository        = customerRepository;
    this.customerAccountRepository = customerAccountRepository;
    this.customerEntityMapper      = customerEntityMapper;
  }

  public CustomerDomain getCustomerInfo(final String customerIdentifier) {
    return customerEntityMapper.toDomain(
        customerRepository.findCustomerEntitiesByIdCustomer(customerIdentifier));
  }

  public List<CustomerDomain> getAllCustomers() {
    return customerEntityMapper.toDomain(
        customerRepository.findAll());
  }

  public AccountInfo createNewAccount(AccountInfo entity) {
    return customerEntityMapper.toDomain(
        customerAccountRepository.save(customerEntityMapper.toEntity(entity)));
  }

}
