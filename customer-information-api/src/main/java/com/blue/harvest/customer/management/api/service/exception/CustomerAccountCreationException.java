package com.blue.harvest.customer.management.api.service.exception;

import java.text.MessageFormat;

public class CustomerAccountCreationException extends RuntimeException {

  private static final String MESSAGE_EXCEPTION = "Error while creating the customer account : {0}";

  public CustomerAccountCreationException(final String message, final Throwable cause) {
    super(MessageFormat.format(MESSAGE_EXCEPTION, message), cause);
  }
}
