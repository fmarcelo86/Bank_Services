package com.bank.enterprise.application.output.port;

import com.bank.enterprise.domain.Enterprise;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnterpriseDb {

  Page<Enterprise> findAll(Pageable pageable);

  Optional<Enterprise> findById(Long id);

  Enterprise save(Enterprise enterprise);

  Enterprise update(Enterprise enterprise);
}