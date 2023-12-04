package com.blue.harvest.customer.management.api.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {

  private DateUtils()
  {

  }

  public static LocalDateTime now()
  {
    return LocalDateTime.now(ZoneOffset.UTC);
  }
}
