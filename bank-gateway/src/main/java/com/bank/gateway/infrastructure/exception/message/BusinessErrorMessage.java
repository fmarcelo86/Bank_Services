package com.bank.gateway.infrastructure.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
  ENTERPRISE_NOT_FOUND("CEB0001", "La empresa no existe"),
  BAD_REQUEST_BODY("CEB0002", "Error in body request");

  private final String code;
  private final String message;
}