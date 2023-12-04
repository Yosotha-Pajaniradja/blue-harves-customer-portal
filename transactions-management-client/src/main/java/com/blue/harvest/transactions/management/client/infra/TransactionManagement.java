package com.blue.harvest.transactions.management.client.infra;

import com.blue.harvest.generated.transactions.management.client.infra.TransactionManagementResourceApi;
import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionCreationResponse;
import com.blue.harvest.transactions.management.client.domain.TransactionCreation;
import com.blue.harvest.transactions.management.client.infra.mapper.TransactionMapper;
import com.blue.harvest.transactions.management.client.service.TransactionAccessDao;
import com.blue.harvest.transactions.management.client.service.TransactionNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.CompletableFuture;

@Component
public class TransactionManagement implements TransactionAccessDao {

  private final String TRANSACTION_CREATION_ERROR = "Error while creating this transaction";

  private final TransactionMapper transactionMapper;

  private final TransactionManagementResourceApi transactionManagementResourceApi;

  @Autowired
  public TransactionManagement(TransactionMapper transactionMapper,
      TransactionManagementResourceApi transactionManagementResourceApi) {
    this.transactionMapper                = transactionMapper;
    this.transactionManagementResourceApi = transactionManagementResourceApi;
  }

  @Override
  public CompletableFuture<TransactionCreationResponse> createTransaction(
      TransactionCreation transactionCreation) {
    return this.transactionManagementResourceApi.createNewTransaction(
            transactionMapper.fromDomainToInfra(transactionCreation))
        .mapNotNull(transactionCreationResponse -> transactionCreationResponse).doOnError(error -> {
          if (error instanceof WebClientResponseException webClientResponseException) {
            final String body = webClientResponseException.getResponseBodyAsString();
            throw new TransactionNotCreatedException(body, webClientResponseException);
          }
          final String body = error.getMessage();
          throw new TransactionNotCreatedException(body, error);
        }).toFuture().toCompletableFuture();
  }
}
