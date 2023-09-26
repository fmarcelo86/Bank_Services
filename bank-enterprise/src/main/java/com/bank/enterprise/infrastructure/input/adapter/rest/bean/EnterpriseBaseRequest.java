package com.bank.enterprise.infrastructure.input.adapter.rest.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class EnterpriseBaseRequest {

  @NotBlank
  String code;
  @NotBlank
  String name;
  @NotBlank
  String description;
  @NotNull
  Boolean active;
}
