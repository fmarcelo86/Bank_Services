package com.bank.enterprise.infrastructure.output.repository.mapper;

import com.bank.enterprise.infrastructure.output.repository.entity.EnterpriseData;
import com.bank.enterprise.domain.Enterprise;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnterpriseDataMapper {

  EnterpriseData toEntity(Enterprise enterprise);

  Enterprise toDomain(EnterpriseData enterprise);
}