package com.blue.harvest.customer.management.api.service;


import com.blue.harvest.customer.management.api.domain.Transaction;
import com.blue.harvest.customer.management.api.service.exception.CustomerAccountCreationException;
import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionCreationResponse;
import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionInfo;
import com.blue.harvest.transactions.management.client.domain.TransactionCreation;
import com.blue.harvest.transactions.management.client.service.TransactionClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@ComponentScan("com.blue.harvest.transactions.management.client")
@ComponentScan("com.blue.generated.harvest.transactions.management.client")
public class CustomerTransactionService {
  public static final String CUSTOMER_TRANSACTION_SERVICE = "Calling customer transaction service";

  private final TransactionClientService transactionClientService;

  @Autowired
  public CustomerTransactionService(TransactionClientService transactionClientService) {
    this.transactionClientService = transactionClientService;
  }

  private static TransactionResponse apply(TransactionInfo transactionInfo) {
    return TransactionResponse.builder()
        .withAccountNumberSource(transactionInfo.getAccountNumberSource())
        .withTransactionCreationDate(transactionInfo.getTransactionCreationDate())
        .withAmount(transactionInfo.getInitialCredit())
        .withAccountNumberTarget(transactionInfo.getAccountNumberTarget()).build();
  }

  public List<TransactionResponse> CreateNewTransaction(final Transaction transaction) {
    try {
      CompletableFuture<TransactionCreationResponse> message =
          transactionClientService.createNewTransaction(TransactionCreation.builder()
              .withAccountNumberSource(transaction.getAccountInfo().getAccountNumberSource())
              .withInitialCredit(transaction.getCreditAmount())
              .withCustomerAccountIdentifier(transaction.getAccountIdentifier())
              .withAccountNumberTarget(transaction.getAccountInfo().getAccountNumberTarget())
              .build());
      TransactionCreationResponse response = message.get();
      if (Objects.nonNull(response.getCountOfSuccess()) && response.getCountOfSuccess() == 1) {
        assert response.getTransactionInfos() != null;
        List<TransactionResponse> list = new ArrayList<>();
        for (TransactionInfo transactionInfo : response.getTransactionInfos()) {
          TransactionResponse apply = apply(transactionInfo);
          list.add(apply);
        }
        return list;
      }
    } catch (ExecutionException | InterruptedException e) {
      throw new CustomerAccountCreationException(e.getMessage(), e);
    }
    return Collections.emptyList();
  }
}
