package com.bank.enterprise.infrastructure.input.adapter.rest.bean;

import com.bank.enterprise.domain.Enterprise;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterpriseResponse {

  Paging paging;
  List<Enterprise> results = List.of();
}
