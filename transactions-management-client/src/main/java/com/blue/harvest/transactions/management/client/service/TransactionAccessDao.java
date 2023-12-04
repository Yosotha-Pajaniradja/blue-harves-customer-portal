package com.blue.harvest.transactions.management.client.service;

import com.blue.harvest.generated.transactions.management.client.infra.model.TransactionCreationResponse;
import com.blue.harvest.transactions.management.client.domain.TransactionCreation;

import java.util.concurrent.CompletableFuture;

public interface TransactionAccessDao {
  CompletableFuture<TransactionCreationResponse> createTransaction(
      final TransactionCreation transactionCreation);
}
