package com.bank.enterprise.infrastructure.input.adapter.rest.bean;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseUpdateRequest extends EnterpriseBaseRequest {

  @NotNull
  Long id;
}