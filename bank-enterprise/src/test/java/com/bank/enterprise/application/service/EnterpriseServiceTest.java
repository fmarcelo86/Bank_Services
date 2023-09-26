package com.bank.enterprise.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bank.enterprise.domain.Enterprise;
import com.bank.enterprise.infrastructure.exception.BusinessException;
import com.bank.enterprise.infrastructure.output.adapter.EnterpriseAdapterRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class EnterpriseServiceTest {

  @InjectMocks
  private EnterpriseService service;
  @Mock
  private EnterpriseAdapterRepository adapter;
  public static final String NAME = "Bank";
  public static final long ID = 1;

  @Test
  @DisplayName("Test method return all enterprise")
  void shouldReturnAllEnterprise() {
    Enterprise enterprise = Enterprise.builder().name(NAME).build();
    Pageable pageable = Pageable.unpaged();
    PageImpl<Enterprise> pageImpl = new PageImpl<>(List.of(enterprise));

    when(adapter.findAll(pageable)).thenReturn(pageImpl);
    Page<Enterprise> enterprisePage = service.getAll(pageable);

    assertEquals(1, enterprisePage.getTotalPages());
    assertEquals(pageImpl.getContent(), enterprisePage.getContent());
  }

  @Test
  @DisplayName("Test method return one enterprise for by id")
  void shouldReturnOneEnterpriseById() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(adapter.findById(ID)).thenReturn(Optional.of(enterprise));
    Enterprise enterpriseResponse = service.getById(ID);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method save and return enterprise")
  void shouldSaveAndReturnEnterprise() {
    Enterprise enterprise = Enterprise.builder().name(NAME).build();

    when(adapter.save(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = service.save(enterprise);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method update and return enterprise")
  void shouldUpdateAndReturnEnterprise() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(adapter.findById(ID)).thenReturn(Optional.of(enterprise));
    when(adapter.update(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = service.update(enterprise);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method to get an exception updating one enterprise non exist")
  void shouldGetExceptionUpdateEnterprise() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(adapter.findById(ID)).thenReturn(Optional.empty());

    assertThrows(BusinessException.class, () -> service.update(enterprise));
  }

  @Test
  @DisplayName("Test method update field active and return enterprise")
  void shouldUpdateFieldActiveAndReturnEnterprise() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(adapter.findById(ID)).thenReturn(Optional.of(enterprise));
    when(adapter.update(enterprise)).thenReturn(enterprise);
    Enterprise enterpriseResponse = service.updateActive(enterprise);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method to get an exception updating field active in enterprise non exist")
  void shouldGetExceptionUpdateFieldActiveEnterprise() {
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(adapter.findById(ID)).thenReturn(Optional.empty());

    assertThrows(BusinessException.class, () -> service.updateActive(enterprise));
  }
}
