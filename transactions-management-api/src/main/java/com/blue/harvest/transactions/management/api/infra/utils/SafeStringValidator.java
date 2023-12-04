package com.blue.harvest.transactions.management.api.infra.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class SafeStringValidator implements ConstraintValidator<SafeString, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return !Objects.nonNull(value) || value.matches("^[\\pL\\pN\\p{Pc}-&;]*$");
  }
}
