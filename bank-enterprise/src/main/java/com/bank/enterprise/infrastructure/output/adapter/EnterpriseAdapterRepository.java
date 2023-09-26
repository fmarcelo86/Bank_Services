package com.bank.enterprise.infrastructure.output.adapter;

import com.bank.enterprise.infrastructure.output.repository.EnterpriseRepository;
import com.bank.enterprise.infrastructure.output.repository.mapper.EnterpriseDataMapper;
import com.bank.enterprise.application.output.port.EnterpriseDb;
import com.bank.enterprise.domain.Enterprise;
import com.bank.enterprise.infrastructure.exception.TechnicalException;
import com.bank.enterprise.infrastructure.exception.message.TechnicalErrorMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnterpriseAdapterRepository implements EnterpriseDb {

  private final EnterpriseRepository repository;
  private final EnterpriseDataMapper mapper;

  @Override
  public Page<Enterprise> findAll(Pageable pageable) {
    try {
      return repository.findAll(pageable).map(mapper::toDomain);
    } catch (Exception e) {
      throw new TechnicalException(e, TechnicalErrorMessage.ENTERPRISE_FIND_ALL);
    }
  }

  @Override
  public Optional<Enterprise> findById(Long id) {
    try {
      return repository.findById(id)
          .map(mapper::toDomain);
    } catch (Exception e) {
      throw new TechnicalException(e, TechnicalErrorMessage.ENTERPRISE_FIND_ONE);
    }
  }

  @Override
  public Enterprise save(Enterprise enterprise) {
    try {
      return Optional.of(enterprise)
          .map(mapper::toEntity)
          .map(repository::save)
          .map(mapper::toDomain)
          .orElseThrow();
    } catch (Exception e) {
      throw new TechnicalException(e, TechnicalErrorMessage.ENTERPRISE_SAVE);
    }
  }

  @Override
  public Enterprise update(Enterprise enterprise) {
    try {
      return Optional.of(enterprise)
          .map(mapper::toEntity)
          .map(repository::save)
          .map(mapper::toDomain)
          .orElseThrow();
    } catch (Exception e) {
      throw new TechnicalException(e, TechnicalErrorMessage.ENTERPRISE_UPDATE);
    }
  }
}
