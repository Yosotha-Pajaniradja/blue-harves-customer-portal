package com.blue.harvest.customer.management.api.infra.jpa.entity;

import com.blue.harvest.customer.management.api.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {
  @Id
  @Column(name = "IDENTIFIER", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "CREATION_DATE", nullable = false)
  private LocalDateTime creationDate;

  @Column(name = "UPDATE_DATE", nullable = false, updatable = true)
  private LocalDateTime updateDate;

  @PrePersist
  protected void onCreate() {
    updateDate = creationDate = DateUtils.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updateDate = DateUtils.now();
  }
}
