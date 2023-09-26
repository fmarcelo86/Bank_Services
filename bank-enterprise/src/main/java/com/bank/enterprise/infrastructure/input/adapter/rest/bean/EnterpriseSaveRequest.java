package com.bank.enterprise.infrastructure.input.adapter.rest.bean;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterpriseSaveRequest extends EnterpriseBaseRequest {

  @Null
  @Hidden
  Long id;
}
