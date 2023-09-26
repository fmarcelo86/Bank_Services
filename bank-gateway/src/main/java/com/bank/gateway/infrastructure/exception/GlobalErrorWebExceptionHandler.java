package com.bank.gateway.infrastructure.exception;

import com.bank.gateway.domain.model.ErrorResponse;
import com.bank.gateway.infrastructure.exception.message.TechnicalErrorMessage;
import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

@Log4j2
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

  private static final String ERROR = "error";

  public GlobalErrorWebExceptionHandler(DefaultErrorAttributes errorAttributes,
      ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {
    super(errorAttributes, new WebProperties().getResources(), applicationContext);
    super.setMessageWriters(serverCodecConfigurer.getWriters());
    super.setMessageReaders(serverCodecConfigurer.getReaders());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(
      final ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
    return Mono.just(request)
        .map(this::getError)
        .flatMap(Mono::error)
        .onErrorResume(TechnicalException.class, GlobalErrorWebExceptionHandler::buildErrorResponse)
        .onErrorResume(BusinessException.class, GlobalErrorWebExceptionHandler::buildErrorResponse)
        .onErrorResume(GlobalErrorWebExceptionHandler::buildErrorResponse)
        .cast(Tuple2.class)
        .map(GlobalErrorWebExceptionHandler::castTo)
        .map(errorStatus -> Tuples.of(errorStatus.getT1(), errorStatus.getT2(), request.path()))
        .flatMap(GlobalErrorWebExceptionHandler::buildResponse)
        .doAfterTerminate(() -> log.error(getError(request)));
  }

  private static Tuple2<ErrorResponse, HttpStatus> castTo(Tuple2<?, ?> errorStatus) {
    return Tuples.of((ErrorResponse) errorStatus.getT1(), (HttpStatus) errorStatus.getT2());
  }

  private static Mono<Tuple2<ErrorResponse, HttpStatus>> buildErrorResponse(
      TechnicalException technicalException) {
    return Mono.just(ErrorResponse.builder()
            .reason(technicalException.getTechnicalErrorMessage().getMessage())
            .code(technicalException.getTechnicalErrorMessage().getCode())
            .message(technicalException.getTechnicalErrorMessage().getMessage())
            .build())
        .zipWith(Mono.just(HttpStatus.BAD_REQUEST));
  }

  private static Mono<Tuple2<ErrorResponse, HttpStatus>> buildErrorResponse(
      BusinessException businessException) {
    return Mono.just(businessException)
        .map(BusinessException::getBusinessErrorMessage)
        .map(errorMsg -> Tuples.of(getErrorResponse(businessException), HttpStatus.BAD_REQUEST));
  }

  private static ErrorResponse getErrorResponse(BusinessException businessException) {
    return ErrorResponse.builder()
        .reason(businessException.getBusinessErrorMessage().getMessage())
        .code(businessException.getBusinessErrorMessage().getCode())
        .message(businessException.getBusinessErrorMessage().getMessage())
        .build();
  }

  private static Mono<Tuple2<ErrorResponse, HttpStatus>> buildErrorResponse(Throwable throwable) {
    return Mono.justOrEmpty(throwable)
        .defaultIfEmpty(throwable)
        .flatMap(Mono::error)
        .onErrorResume(ResponseStatusException.class, exception -> Mono.just(ErrorResponse.builder()
            .reason(TechnicalErrorMessage.SERVICE_NOT_FOUND.getMessage())
            .code(TechnicalErrorMessage.SERVICE_NOT_FOUND.getCode())
            .message(TechnicalErrorMessage.SERVICE_NOT_FOUND.getMessage())
            .build()))
        .onErrorResume(ConnectException.class, exception -> Mono.just(ErrorResponse.builder()
            .reason(TechnicalErrorMessage.SERVICE_UNAVAILABLE.getMessage())
            .code(TechnicalErrorMessage.SERVICE_UNAVAILABLE.getCode())
            .message(TechnicalErrorMessage.SERVICE_UNAVAILABLE.getMessage())
            .build()))
        .onErrorResume(Throwable.class, exception -> Mono.just(ErrorResponse.builder()
            .reason(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
            .code(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode())
            .message(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
            .build()))
        .ofType(ErrorResponse.class)
        .zipWith(Mono.just(HttpStatus.BAD_GATEWAY));
  }

  private static Mono<ServerResponse> buildResponse(
      Tuple3<ErrorResponse, HttpStatus, String> errorStatus) {
    return ServerResponse.status(errorStatus.getT2())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(getErrors(errorStatus));
  }

  private static Map<String, ErrorResponse> getErrors(
      Tuple3<ErrorResponse, HttpStatus, String> errorStatus) {
    return Collections.singletonMap(ERROR, errorStatus.getT1().toBuilder()
        .domain(errorStatus.getT3()).build());
  }
}