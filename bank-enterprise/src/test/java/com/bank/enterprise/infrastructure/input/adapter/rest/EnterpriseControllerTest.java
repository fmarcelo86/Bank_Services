package com.bank.enterprise.infrastructure.input.adapter.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseResponse;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseSaveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateActiveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.mapper.EnterpriseMapper;
import com.bank.enterprise.application.service.EnterpriseService;
import com.bank.enterprise.domain.Enterprise;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class EnterpriseControllerTest {

  @InjectMocks
  private EnterpriseController controller;
  @Mock
  private EnterpriseService service;
  @Spy
  private EnterpriseMapper mapper = Mappers.getMapper(EnterpriseMapper.class);
  public static final String NAME = "Bank";
  public static final long ID = 1;

  @Test
  @DisplayName("Test method return all enterprise")
  void shouldReturnAllEnterprise() {
    Enterprise enterprise = Enterprise.builder().name(NAME).build();
    Pageable pageable = Pageable.unpaged();
    PageImpl<Enterprise> pageImpl = new PageImpl<>(List.of(enterprise));
    mapper.toBean(null);
    ReflectionTestUtils.invokeMethod(mapper, "enterprisePageToPaging", (Page<Enterprise>) null);

    when(service.getAll(pageable)).thenReturn(pageImpl);
    EnterpriseResponse enterpriseResponse = controller.getAll(pageable);

    assertEquals(1, enterpriseResponse.getPaging().getTotalPages());
    assertEquals(pageImpl.getContent(), enterpriseResponse.getResults());
  }

  @Test
  @DisplayName("Test method return one enterprise for by id")
  void shouldReturnOneEnterpriseById() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(service.getById(ID)).thenReturn(enterprise);
    Enterprise enterpriseResponse = controller.getById(ID);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method save and return enterprise")
  void shouldSaveAndReturnEnterprise() {
    EnterpriseSaveRequest enterpriseSaveRequest = new EnterpriseSaveRequest();
    enterpriseSaveRequest.setName(NAME);

    Enterprise enterprise = mapper.toDomain(enterpriseSaveRequest);

    when(service.save(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = controller.save(enterpriseSaveRequest);

    assertEquals(enterprise.getName(), enterpriseResponse.getName());
  }

  @Test
  @DisplayName("Test method update and return enterprise")
  void shouldUpdateAndReturnEnterprise() {
    EnterpriseUpdateRequest enterpriseUpdateRequest = new EnterpriseUpdateRequest();
    enterpriseUpdateRequest.setId(ID);
    enterpriseUpdateRequest.setName(NAME);
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();
    mapper.toDomain((EnterpriseSaveRequest) null);
    mapper.toDomain((EnterpriseUpdateRequest) null);
    mapper.toDomain((EnterpriseUpdateActiveRequest) null);

    when(service.update(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = controller.update(enterpriseUpdateRequest);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method update field active and return enterprise")
  void shouldUpdateFieldActiveAndReturnEnterprise() {
    EnterpriseUpdateActiveRequest enterpriseUpdateActiveRequest = new EnterpriseUpdateActiveRequest();
    enterpriseUpdateActiveRequest.setId(ID);
    enterpriseUpdateActiveRequest.setActive(true);
    Enterprise enterprise = Enterprise.builder().id(ID).active(true).build();

    when(service.updateActive(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = controller.updateActive(enterpriseUpdateActiveRequest);

    assertEquals(enterprise, enterpriseResponse);
  }
}
