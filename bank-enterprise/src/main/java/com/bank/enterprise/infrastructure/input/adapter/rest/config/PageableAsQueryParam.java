package com.bank.enterprise.infrastructure.input.adapter.rest.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
    , description = "Índice de página a consultar de base cero (0..N)"
    , name = "page"
    , schema = @Schema(type = "integer", defaultValue = "0"))
@Parameter(in = ParameterIn.QUERY
    , description = "El tamaño de registros por página"
    , name = "size"
    , schema = @Schema(type = "integer", defaultValue = "5"))
public @interface
PageableAsQueryParam {

}