package com.bank.enterprise.infrastructure.output.repository;

import com.bank.enterprise.infrastructure.output.repository.entity.EnterpriseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseData, Long> {
}
