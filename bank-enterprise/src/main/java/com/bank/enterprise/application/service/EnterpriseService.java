package com.bank.enterprise.application.service;

import com.bank.enterprise.domain.Enterprise;
import com.bank.enterprise.infrastructure.exception.BusinessException;
import com.bank.enterprise.infrastructure.exception.message.BusinessErrorMessage;
import com.bank.enterprise.infrastructure.output.adapter.EnterpriseAdapterRepository;
import com.bank.enterprise.application.input.port.EnterpriseApi;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterpriseService implements EnterpriseApi {

  private final EnterpriseAdapterRepository adapter;

  @Override
  public Page<Enterprise> getAll(Pageable pageable) {
    return adapter.findAll(pageable);
  }

  @Override
  public Enterprise getById(Long id) {
    return getEnterprise(id)
        .orElseThrow();
  }

  @Override
  public Enterprise save(Enterprise enterprise) {
    return adapter.save(enterprise);
  }

  @Override
  public Enterprise update(Enterprise enterprise) {
    getEnterprise(enterprise.getId());
    return adapter.update(enterprise);
  }

  @Override
  public Enterprise updateActive(Enterprise enterprise) {
    return getEnterprise(enterprise.getId())
        .map(Enterprise::toBuilder)
        .map(enterpriseBuilder -> enterpriseBuilder.active(enterprise.getActive()).build())
        .map(adapter::update)
        .orElseThrow();
  }

  private Optional<Enterprise> getEnterprise(Long id) {
    Optional<Enterprise> opEnterprise = adapter.findById(id);
    if (opEnterprise.isEmpty()) {
      throw new BusinessException(BusinessErrorMessage.ENTERPRISE_NOT_FOUND);
    }
    return opEnterprise;
  }
}