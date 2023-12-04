package com.blue.harvest.customer.management.api.infra.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDetails {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private long id = 0;

  private String title;

  private String description;

  private boolean published;


  public CustomerDetails(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }


  @Override
  public String toString() {
    return "Customer Data [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
