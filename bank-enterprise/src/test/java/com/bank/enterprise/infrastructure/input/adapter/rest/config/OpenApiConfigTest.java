package com.bank.enterprise.infrastructure.input.adapter.rest.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

  @InjectMocks
  private OpenApiConfig openApiConfig;

  @Test
  void shouldConfigAndReturnOpenApi() {
    Assertions.assertAll(() -> openApiConfig.openAPI());
  }
}
