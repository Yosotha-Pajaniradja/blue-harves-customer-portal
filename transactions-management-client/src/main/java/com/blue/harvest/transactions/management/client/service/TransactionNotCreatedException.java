package com.blue.harvest.transactions.management.client.service;

public class TransactionNotCreatedException extends RuntimeException {
  public TransactionNotCreatedException(final String errorMessage, final Throwable err) {
    super(errorMessage, err);
  }
}
