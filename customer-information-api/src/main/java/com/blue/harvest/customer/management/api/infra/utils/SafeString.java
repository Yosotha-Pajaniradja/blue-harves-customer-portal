package com.blue.harvest.customer.management.api.infra.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SafeStringValidator.class})
@Documented
public @interface SafeString {
  String message() default "The String contains forbidden Characters";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
