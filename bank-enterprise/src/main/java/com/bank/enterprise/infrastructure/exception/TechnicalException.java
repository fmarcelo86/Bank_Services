package com.bank.enterprise.infrastructure.exception;

import com.bank.enterprise.infrastructure.exception.message.TechnicalErrorMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnicalException extends RuntimeException {

  final TechnicalErrorMessage technicalErrorMessage;

  public TechnicalException(Throwable cause, TechnicalErrorMessage technicalErrorMessage) {
    super(cause);
    this.technicalErrorMessage = technicalErrorMessage;
  }
}