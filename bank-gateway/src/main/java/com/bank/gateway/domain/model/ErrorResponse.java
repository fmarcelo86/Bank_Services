package com.bank.gateway.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

  String code;
  String message;
  String reason;
  String domain;
}