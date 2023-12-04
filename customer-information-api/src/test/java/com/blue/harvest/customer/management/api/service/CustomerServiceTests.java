package com.blue.harvest.customer.management.api.service;

import com.blue.harvest.customer.management.api.infra.jpa.repository.CustomerDao;
import com.blue.harvest.customer.management.api.mapper.CustomerDtoMapper;
import com.blue.harvest.transactions.management.client.service.TransactionAccessDao;
import com.blue.harvest.transactions.management.client.service.TransactionClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {
  @SpyBean CustomerService customerService;
  @SpyBean CustomerDao customerDao;
  @SpyBean CustomerDtoMapper customerDtoMapper;
  @SpyBean CustomerTransactionService customerTransactionService;
  @SpyBean TransactionClientService transactionClientService;
  @SpyBean TransactionAccessDao accessDao;

  @Test
  void get_customer_info_should_throw_exception() {
    assertThrows(NullPointerException.class, () -> customerService.createCustomerAccount(null));
  }

  //  @Test
  //  void get_customer_info_should_get_custoemr_info() {
  //    when(customerDao.getCustomerInfo(any(String.class))).thenReturn(
  //        CustomerDomain.builder().withNoOfValidAccounts(2).withIdCustomer("Identifier").build());
  //    final TransactionCreationResponse creationResponse = new TransactionCreationResponse();
  //    final TransactionInfo             transactionInfo  = new TransactionInfo();
  //    transactionInfo.setTransactionCreationDate(LocalDate.now());
  //    transactionInfo.setAccountNumberTarget("targetAccountNumber");
  //    transactionInfo.setInitialCredit(BigDecimal.ONE);
  //    creationResponse.addTransactionInfosItem(transactionInfo);
  //    creationResponse.setCountOfSuccess(1);
  //
  //    when(transactionClientService.createNewTransaction(any())).thenReturn(
  //        CompletableFuture.completedFuture(creationResponse));
  //    CustomerResponseDto responseDto =
  //        customerService.createCustomerAccount(customerDtoMapper.toDomain(buildCustomerDetails()));
  //  }


}
