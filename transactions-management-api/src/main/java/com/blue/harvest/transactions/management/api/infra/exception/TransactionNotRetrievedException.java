package com.blue.harvest.transactions.management.api.infra.exception;

import java.text.MessageFormat;

public class TransactionNotRetrievedException extends RuntimeException {
  public TransactionNotRetrievedException(final String message) {
    super(message);
  }
  public TransactionNotRetrievedException(final String mesage, final Object... params) {
    super(MessageFormat.format(mesage, params));
  }
}
