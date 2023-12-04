package com.blue.harvest.transactions.management.api.infra.dto;

import lombok.Getter;

public enum TaxProfile {
  RETAIL("Retail Profile"),
  INSTIT("Institutional Profile");

  @Getter
  private final String label;

  TaxProfile(final String label) {
    this.label = label;
  }
}
