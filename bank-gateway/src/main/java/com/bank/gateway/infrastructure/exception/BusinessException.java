package com.bank.gateway.infrastructure.exception;

import com.bank.gateway.infrastructure.exception.message.BusinessErrorMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessException extends RuntimeException {

  final BusinessErrorMessage businessErrorMessage;
}