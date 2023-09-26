package com.bank.enterprise.infrastructure.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {
  SERVICE_NOT_FOUND("CET0001", "Service not found"),
  UNEXPECTED_EXCEPTION("CET0002", "Unexpected error"),
  ENTERPRISE_FIND_ALL("CET0003", "Error consultando todas las empresas"),
  ENTERPRISE_FIND_ONE("CET0004", "Error consultando la empresa"),
  ENTERPRISE_SAVE("CET0005", "Error guardando la empresa"),
  ENTERPRISE_UPDATE("CET0006", "Error actualizando la empresa");

  private final String code;
  private final String message;
}