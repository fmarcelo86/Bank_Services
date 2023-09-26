package com.bank.enterprise.infrastructure.exception;

import com.bank.enterprise.infrastructure.exception.message.BusinessErrorMessage;
import com.bank.enterprise.infrastructure.exception.message.TechnicalErrorMessage;
import com.bank.enterprise.domain.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleExceptions(BusinessException ex) {
    log.error(ex);
    return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TechnicalException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleExceptions(TechnicalException ex) {
    log.error(ex);
    return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleExceptions(Exception ex) {
    log.error(ex);
    return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST);
  }

  private static ErrorResponse getErrorResponses(BusinessException businessException) {
    return ErrorResponse.builder()
        .code(businessException.getBusinessErrorMessage().getCode())
        .message(businessException.getBusinessErrorMessage().getMessage())
        .build();
  }

  private static ErrorResponse getErrorResponses(TechnicalException technicalException) {
    return ErrorResponse.builder()
        .code(technicalException.getTechnicalErrorMessage().getCode())
        .message(technicalException.getTechnicalErrorMessage().getMessage())
        .build();
  }

  private static ErrorResponse getErrorResponses(Exception ex) {
    ErrorResponse errorResponse;
    try {
      throw ex;
    } catch (HttpRequestMethodNotSupportedException e) {
      errorResponse = ErrorResponse.builder()
          .code(TechnicalErrorMessage.SERVICE_NOT_FOUND.getCode())
          .message(TechnicalErrorMessage.SERVICE_NOT_FOUND.getMessage())
          .build();
    } catch (MethodArgumentNotValidException e) {
      errorResponse = ErrorResponse.builder()
          .code(BusinessErrorMessage.BAD_REQUEST_BODY.getCode())
          .message(BusinessErrorMessage.BAD_REQUEST_BODY.getMessage())
          .build();
    } catch (Exception e) {
      errorResponse = ErrorResponse.builder()
          .code(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode())
          .message(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
          .build();
    }
    return errorResponse;
  }
}