package com.blue.harvest.customer.management.api.service;


import com.blue.harvest.customer.management.api.domain.AccountInfo;
import com.blue.harvest.customer.management.api.domain.CustomerDomain;
import com.blue.harvest.customer.management.api.domain.Transaction;
import com.blue.harvest.customer.management.api.infra.dto.AccountsInfoDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerResponseDto;
import com.blue.harvest.customer.management.api.infra.dto.CustomerTransactionDto;
import com.blue.harvest.customer.management.api.infra.jpa.repository.CustomerDao;
import com.blue.harvest.customer.management.api.service.exception.CustomerAccountCreationException;
import com.blue.harvest.customer.management.api.service.exception.CustomerAccountDoesNotExist;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerService {

  private final CustomerDao customerDao;

  private final CustomerTransactionService customerTransactionService;

  public CustomerService(final CustomerDao customerDao,
      final CustomerTransactionService customerTransactionService) {
    this.customerDao                = customerDao;
    this.customerTransactionService = customerTransactionService;
  }

  public CustomerResponseDto createCustomerAccount(final CustomerDomain customerDomain) {
    try {
      final CustomerDomain customerInfo =
          customerDao.getCustomerInfo(customerDomain.getIdCustomer());
      final Transaction transaction = customerDomain.getTransaction();
      if (Objects.nonNull(customerInfo)) {
        return controlCustomerInfo(customerInfo, transaction);
      }
    } catch (CustomerAccountDoesNotExist e) {
      throw new CustomerAccountDoesNotExist(e.getMessage(), e);
    }
    return null;
  }

  private CustomerResponseDto controlCustomerInfo(CustomerDomain customerInfo,
      Transaction transaction) {
    if (customerInfo.getNoOfValidAccounts() >= 0) {
      Transaction         transactionCreated = createNewAccount(customerInfo, transaction);
      TransactionResponse creationResponse   = triggerNewTransaction(transactionCreated);
      if (Objects.nonNull(creationResponse)) {
        return CustomerResponseDto.builder().withCustomerTransactionDto(
                CustomerTransactionDto.builder().withCustomerIdentifier(customerInfo.getIdCustomer())
                    .withLastName(customerInfo.getLastName()).withFirstName(customerInfo.getFirstName())
                    .withInitialCredit(transactionCreated.getCreditAmount())
                    .withNewAccountNumber(creationResponse.getAccountNumberTarget()).build())
            .withMessage(
                "The new account number has been successfully created and credit with initial credit")
            .build();
      } else {
        return CustomerResponseDto.builder().withCustomerTransactionDto(
                CustomerTransactionDto.builder().withCustomerIdentifier(customerInfo.getIdCustomer())
                    .withLastName(customerInfo.getLastName()).withFirstName(customerInfo.getFirstName())
                    .withInitialCredit(transactionCreated.getCreditAmount()).build())
            .withMessage("The Account cannot be created, please check with the support").build();
      }
    }
    return null;
  }

  private TransactionResponse triggerNewTransaction(Transaction transaction) {
    return customerTransactionService.createNewTransaction(transaction).get(0);

  }

  private Transaction createNewAccount(CustomerDomain customerInfo, Transaction transaction) {
    try {
      final String uniqueID = UUID.randomUUID().toString().replace("-", "");
      if (transaction.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
        AccountInfo accountInfo =
            AccountInfo.builder().withCustomerIdentifier(customerInfo.getIdCustomer())
                .withAccountNumberTarget(uniqueID).build();
        customerDao.createNewAccount(accountInfo);
        transaction.setAccountInfo(
            AccountsInfoDto.builder().withAccountNumberTarget(uniqueID).build());
        transaction.setCreditAmount(transaction.getCreditAmount());
        transaction.setAccountIdentifier(customerInfo.getIdCustomer());
        return transaction;
      }
    } catch (Exception e) {
      throw new CustomerAccountCreationException("Error while creating customer account", e);
    }

    return transaction;
  }

  public CustomerDomain findByCustomerIdentifier(String id) {
    return customerDao.getCustomerInfo(id);
  }

  public List<CustomerDomain> findAll() {
    return customerDao.getAllCustomers();
  }

}
