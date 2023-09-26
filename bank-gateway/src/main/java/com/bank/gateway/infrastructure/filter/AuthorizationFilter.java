package com.bank.gateway.infrastructure.filter;

import com.bank.gateway.infrastructure.bean.EnterpriseRequest;
import com.bank.gateway.infrastructure.exception.BusinessException;
import com.bank.gateway.infrastructure.exception.message.BusinessErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

  private final ObjectMapper objectMapper;
  private final Validator validator;

  public AuthorizationFilter(ObjectMapper objectMapper, Validator validator) {
    super(Config.class);
    this.objectMapper = objectMapper;
    this.validator = validator;
  }

  private final List<HttpMessageReader<?>> messageReaders = getMessageReaders();

  private List<HttpMessageReader<?>> getMessageReaders() {
    return HandlerStrategies.withDefaults().messageReaders();
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {

      if (HttpMethod.POST.equals(exchange.getRequest().getMethod())) {
        return logRequestBody(exchange, chain);
      } else {
        return chain.filter(exchange);
      }
    };
  }

  private Mono<Void> logRequestBody(ServerWebExchange exchange, GatewayFilterChain chain) {
    return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
      DataBufferUtils.retain(dataBuffer);

      Flux<DataBuffer> cachedFlux = Flux.defer(
          () -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));

      ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
        @Override
        public Flux<DataBuffer> getBody() {
          return cachedFlux;
        }
      };

      return ServerRequest.create(exchange.mutate().request(mutatedRequest).build(), messageReaders)
          .bodyToMono(String.class)
          .flatMap(this::getEnterprise)
          .map(enterpriseRequest -> enterpriseRequest)
          .map(validator::validate)
          .map(constraintViolations -> constraintViolations)
          .map(Set::isEmpty)
          .filter(valid -> valid)
          .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)))
          .then(chain.filter(exchange.mutate().request(mutatedRequest).build()));
    });
  }

  private Mono<EnterpriseRequest> getEnterprise(String body) {
    try {
      return Mono.just(objectMapper.readValue(body, EnterpriseRequest.class));
    } catch (IOException e) {
      return Mono.error(e);
    }
  }

  @Data
  public static class Config {

  }
}
