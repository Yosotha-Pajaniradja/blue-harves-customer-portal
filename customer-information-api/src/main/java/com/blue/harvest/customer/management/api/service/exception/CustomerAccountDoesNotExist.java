package com.blue.harvest.customer.management.api.service.exception;

import java.text.MessageFormat;

public class CustomerAccountDoesNotExist extends RuntimeException {

  private static final String MESSAGE_EXCEPTION = "Error while access customer : {0}";

  public CustomerAccountDoesNotExist(final String message, final Throwable cause) {
    super(MessageFormat.format(MESSAGE_EXCEPTION, message), cause);
  }
}
