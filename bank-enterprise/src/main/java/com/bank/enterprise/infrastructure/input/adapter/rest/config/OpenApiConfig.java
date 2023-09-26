package com.bank.enterprise.infrastructure.input.adapter.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Gestión de empresas")
            .version("1.0.1")
            .description("API Rest para administrar el catalogo de empresas")
            .termsOfService("http://swagger.io/terms/")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .components(
            new Components().addSecuritySchemes("Bearer-Authentication", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
  }
}
