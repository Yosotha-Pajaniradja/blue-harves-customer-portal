package com.blue.harvest.customer.management.api.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
public class AccountsDomain {
  String accountNumber;
  LocalDateTime validityDate;
  LocalDateTime creationDate;
}
