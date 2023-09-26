package com.bank.enterprise.infrastructure.input.adapter.rest;

import com.bank.enterprise.application.service.EnterpriseService;
import com.bank.enterprise.domain.Enterprise;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseResponse;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseSaveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateActiveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.config.PageableAsQueryParam;
import com.bank.enterprise.infrastructure.input.adapter.rest.mapper.EnterpriseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/catalog/enterprises")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer-Authentication")
@Tag(name = "Enterprise", description = "Gestiona el catálogo de empresas")
public class EnterpriseController {

  private final EnterpriseService service;
  private final EnterpriseMapper mapper;

  @PageableAsQueryParam
  @Operation(summary = "Consultar todas las empresas", description = "Permite consultar todas las empresa")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public EnterpriseResponse getAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = "id")
  Pageable pageable) {
    return Optional.of(pageable)
        .map(service::getAll)
        .map(mapper::toBean)
        .orElseThrow();
  }

  @Operation(summary = "Consultar empresas por id", description = "Permite consultar una empresa por id")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Enterprise getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @Operation(summary = "Guardar una empresa", description = "Permite guardar una empresa")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Enterprise save(@RequestBody @Valid EnterpriseSaveRequest enterprise) {
    return Optional.of(enterprise)
        .map(mapper::toDomain)
        .map(service::save)
        .orElseThrow();
  }

  @Operation(summary = "Actualizar una empresa", description = "Permite actualizar una empresa")
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Enterprise update(@RequestBody @Valid EnterpriseUpdateRequest enterprise) {
    return Optional.of(enterprise)
        .map(mapper::toDomain)
        .map(service::update)
        .orElseThrow();
  }

  @Operation(summary = "Activar e inactivar una empresa", description = "Permite activar o inactivar una empresa")
  @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Enterprise updateActive(@RequestBody @Valid EnterpriseUpdateActiveRequest enterprise) {
    return Optional.of(enterprise)
        .map(mapper::toDomain)
        .map(service::updateActive)
        .orElseThrow();
  }
}
