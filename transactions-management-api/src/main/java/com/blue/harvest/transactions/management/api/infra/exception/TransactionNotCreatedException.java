package com.blue.harvest.transactions.management.api.infra.exception;

import java.text.MessageFormat;

public class TransactionNotCreatedException extends RuntimeException {
  public TransactionNotCreatedException(final String message) {
    super(message);
  }
  public TransactionNotCreatedException(final String mesage, final Object... params) {
    super(MessageFormat.format(mesage, params));
  }
}
