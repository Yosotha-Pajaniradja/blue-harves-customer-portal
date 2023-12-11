package com.blue.harvest.customer.management.api.infra.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.blue.harvest.customer.management.api.infra.jpa.entity.CustomerAccountEntity}
 */
@Data
@Builder(setterPrefix = "with")
public class AccountsInfoDto implements Serializable {

  String accountNumberTarget;
  LocalDateTime validityDate;
  LocalDateTime creationDate;
}
