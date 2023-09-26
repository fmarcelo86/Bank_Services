package com.bank.enterprise.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enterprise {

  Long id;
  String code;
  String name;
  String description;
  Boolean active;
}
