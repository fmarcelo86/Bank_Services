package com.bank.gateway.infrastructure.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterpriseRequest {

  @NotBlank
  String code;
  @NotBlank
  String name;
  @NotBlank
  String description;
  @NotNull
  Boolean active;
}
