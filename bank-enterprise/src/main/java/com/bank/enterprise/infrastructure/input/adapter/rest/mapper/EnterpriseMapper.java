package com.bank.enterprise.infrastructure.input.adapter.rest.mapper;

import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseResponse;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseSaveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateActiveRequest;
import com.bank.enterprise.infrastructure.input.adapter.rest.bean.EnterpriseUpdateRequest;
import com.bank.enterprise.domain.Enterprise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnterpriseMapper {

  Enterprise toDomain(EnterpriseSaveRequest enterprise);

  Enterprise toDomain(EnterpriseUpdateRequest enterprise);

  Enterprise toDomain(EnterpriseUpdateActiveRequest enterprise);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "paging.totalPages", source = "totalPages")
  @Mapping(target = "paging.totalRecords", source = "totalElements")
  @Mapping(target = "paging.currentPage", source = "number")
  @Mapping(target = "paging.sizePage", source = "size")
  @Mapping(target = "results", source = "content")
  EnterpriseResponse toBean(Page<Enterprise> enterprisePage);
}