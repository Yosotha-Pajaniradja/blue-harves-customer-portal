package com.blue.harvest.customer.management.api.service.exception;

import java.text.MessageFormat;

public class CustomerAccountNotCreated extends RuntimeException {

  private static final String MESSAGE_EXCEPTION = "Error while creating new Account: {0}";

  public CustomerAccountNotCreated(final String message, final Throwable cause) {
    super(MessageFormat.format(MESSAGE_EXCEPTION, message), cause);
  }
}
