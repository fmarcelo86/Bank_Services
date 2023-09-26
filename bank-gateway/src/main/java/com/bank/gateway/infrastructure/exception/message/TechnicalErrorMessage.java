package com.bank.gateway.infrastructure.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {
  SERVICE_NOT_FOUND("CET0001", "Service not found"),
  UNEXPECTED_EXCEPTION("CET0002", "Unexpected error"),
  SERVICE_UNAVAILABLE("CET0003", "Service Unavailable");

  private final String code;
  private final String message;
}