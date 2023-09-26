package com.bank.enterprise.infrastructure.output.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bank.enterprise.infrastructure.output.repository.EnterpriseRepository;
import com.bank.enterprise.infrastructure.output.repository.entity.EnterpriseData;
import com.bank.enterprise.infrastructure.output.repository.mapper.EnterpriseDataMapper;
import com.bank.enterprise.domain.Enterprise;
import com.bank.enterprise.infrastructure.exception.TechnicalException;
import java.util.List;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
class EnterpriseAdapterRepositoryTest {

  @InjectMocks
  private EnterpriseAdapterRepository adapter;
  @Mock
  private EnterpriseRepository repository;
  @Spy
  private EnterpriseDataMapper mapper = Mappers.getMapper(EnterpriseDataMapper.class);

  public static final String NAME = "Bank";
  public static final long ID = 1;

  @Test
  @DisplayName("Test method return all enterprise")
  void shouldReturnAllEnterprise() {
    EnterpriseData enterprise = EnterpriseData.builder().name(NAME).build();
    Pageable pageable = Pageable.unpaged();
    PageImpl<EnterpriseData> pageImpl = new PageImpl<>(List.of(enterprise));

    when(repository.findAll(pageable)).thenReturn(pageImpl);
    Page<Enterprise> enterprisePage = adapter.findAll(pageable);

    assertEquals(1, enterprisePage.getTotalPages());
    assertEquals(pageImpl.getContent().get(0).getId(), enterprisePage.getContent().get(0).getId());
  }

  @Test
  @DisplayName("Test method to get exception find all enterprise")
  void shouldGetExceptionFindAllEnterprise() {
    Pageable pageable = Pageable.unpaged();

    when(repository.findAll()).thenThrow(RuntimeException.class);

    assertThrows(TechnicalException.class, () -> adapter.findAll(pageable));
  }

  @Test
  @DisplayName("Test method return on enterprise for by id")
  void shouldReturnOneEnterpriseById() {
    EnterpriseData enterprise = EnterpriseData.builder().name(NAME).build();

    when(repository.findById(ID)).thenReturn(Optional.of(enterprise));
    Enterprise enterpriseResponse = adapter.findById(ID).orElseThrow();

    assertEquals(enterprise.getName(), enterpriseResponse.getName());
  }

  @Test
  @DisplayName("Test method to get exception find one enterprise for by id")
  void shouldGetExceptionFindOneEnterprise() {
    when(repository.findById(ID)).thenThrow(RuntimeException.class);

    assertThrows(TechnicalException.class, () -> adapter.findById(ID));
  }

  @Test
  @DisplayName("Test method save and return enterprise")
  void shouldSaveAndReturnEnterprise() {
    EnterpriseData enterpriseData = EnterpriseData.builder().name(NAME).build();
    Enterprise enterprise = mapper.toDomain(enterpriseData);

    when(repository.save(enterpriseData)).thenReturn(enterpriseData);
    Enterprise enterpriseResponse = adapter.save(enterprise);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method to get exception saving enterprise")
  void shouldGetExceptionSaveEnterprise() {
    EnterpriseData enterpriseData = EnterpriseData.builder().name(NAME).build();
    Enterprise enterprise = Enterprise.builder().name(NAME).build();

    when(repository.save(enterpriseData)).thenThrow(RuntimeException.class);

    assertThrows(TechnicalException.class, () -> adapter.save(enterprise));
  }

  @Test
  @DisplayName("Test method update and return enterprise")
  void shouldUpdateAndReturnEnterprise() {
    EnterpriseData enterpriseData = EnterpriseData.builder().id(ID).name(NAME).build();
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();
    mapper.toEntity(null);
    mapper.toDomain(null);

    when(repository.save(enterpriseData)).thenReturn(enterpriseData);
    Enterprise enterpriseResponse = adapter.update(enterprise);

    assertEquals(enterprise, enterpriseResponse);
  }

  @Test
  @DisplayName("Test method to get an exception updating one enterprise")
  void shouldGetExceptionUpdateEnterprise() {
    EnterpriseData enterpriseData = EnterpriseData.builder().id(ID).name(NAME).build();
    Enterprise enterprise = Enterprise.builder().id(ID).name(NAME).build();

    when(repository.save(enterpriseData)).thenThrow(RuntimeException.class);

    assertThrows(TechnicalException.class, () -> adapter.update(enterprise));
  }
}