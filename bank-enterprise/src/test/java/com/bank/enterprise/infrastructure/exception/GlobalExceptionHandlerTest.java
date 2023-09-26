package com.bank.enterprise.infrastructure.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.bank.enterprise.domain.ErrorResponse;
import com.bank.enterprise.infrastructure.exception.message.BusinessErrorMessage;
import com.bank.enterprise.infrastructure.exception.message.TechnicalErrorMessage;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks
  private GlobalExceptionHandler exceptionHandler;

  @Test
  @DisplayName("Test method handle business exception")
  void shouldHandleBusinessException() {
    BusinessException exception = new BusinessException(BusinessErrorMessage.ENTERPRISE_NOT_FOUND);
    ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(BusinessErrorMessage.ENTERPRISE_NOT_FOUND.getCode(), Objects.requireNonNull(
        responseEntity.getBody()).getCode());
  }

  @Test
  @DisplayName("Test method handle technical exception")
  void shouldHandleTechnicalException() {
    TechnicalException exception = new TechnicalException(
        TechnicalErrorMessage.UNEXPECTED_EXCEPTION);
    ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode(), Objects.requireNonNull(
        responseEntity.getBody()).getCode());
  }

  @Test
  @DisplayName("Test method handle any HttpRequestMethodNotSupportedException")
  void shouldHandleHttpRequestMethodNotSupportedException() {
    Exception exception = new HttpRequestMethodNotSupportedException("");
    ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(TechnicalErrorMessage.SERVICE_NOT_FOUND.getCode(), Objects.requireNonNull(
        responseEntity.getBody()).getCode());
  }

  @Test
  @DisplayName("Test method handle any MethodArgumentNotValidException")
  void shouldHandleMethodArgumentNotValidException() {
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(BusinessErrorMessage.BAD_REQUEST_BODY.getCode(), Objects.requireNonNull(
        responseEntity.getBody()).getCode());
  }

  @Test
  @DisplayName("Test method handle any exception")
  void shouldHandleAnyException() {
    RuntimeException exception = new RuntimeException();
    ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode(), Objects.requireNonNull(
        responseEntity.getBody()).getCode());
  }
}
