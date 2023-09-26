package com.bank.enterprise.application.input.port;

import com.bank.enterprise.domain.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnterpriseApi {

  Page<Enterprise> getAll(Pageable pageable);

  Enterprise getById(Long id);

  Enterprise save(Enterprise enterprise);

  Enterprise update(Enterprise enterprise);

  Enterprise updateActive(Enterprise enterprise);
}