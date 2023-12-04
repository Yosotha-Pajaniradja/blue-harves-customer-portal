package com.blue.harvest.customer.management.api.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class AccountInfo {
  private String accountNumberTarget;
  private String accountNumberSource;
  private LocalDate accountCreationDate;
  private LocalDateTime accountValidityDate;

}
