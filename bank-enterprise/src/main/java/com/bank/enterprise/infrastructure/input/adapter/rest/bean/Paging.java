package com.bank.enterprise.infrastructure.input.adapter.rest.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Paging {

  Integer totalPages;
  Integer currentPage;
  Integer sizePage;
  Long totalRecords;
}
