package com.blue.harvest.transactions.management.client.utils;

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
