package com.bank.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.boot.SpringApplication.run;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;
import org.springframework.boot.SpringApplication;
import org.springframework.context.support.GenericApplicationContext;

class MainApplicationTest {

  @Test
  void shouldReturnApplicationContext() {
    String[] args = {};
    GenericApplicationContext context = new GenericApplicationContext();

    try (MockedStatic<SpringApplication> utilities = mockStatic(SpringApplication.class)) {
      Verification verification = (Verification) run(MainApplication.class, args);
      utilities.when(verification).thenReturn(context);
      assertAll(() -> MainApplication.main(args));
      assertEquals(context, run(MainApplication.class, args));
      assertAll(MainApplication::new);
    }
  }
}