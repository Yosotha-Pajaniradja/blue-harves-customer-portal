package com.blue.harvest.transactions.management.client.service;

import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionCreationResponse;
import com.blue.harvest.transactions.management.client.domain.TransactionCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransactionClientService {
  private final TransactionAccessDao accessDao;

  @Autowired
  public TransactionClientService(final TransactionAccessDao accessDao) {
    this.accessDao = accessDao;
  }

  public CompletableFuture<TransactionCreationResponse> createNewTransaction(final TransactionCreation creation)
  {
    return this.accessDao.createTransaction(creation);
  }

}
