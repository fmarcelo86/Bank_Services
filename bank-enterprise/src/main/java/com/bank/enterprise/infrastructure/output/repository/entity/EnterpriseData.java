package com.bank.enterprise.infrastructure.output.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder(toBuilder = true)
@DynamicUpdate
@Entity(name = "Enterprise")
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String code;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  @ColumnDefault("true")
  private Boolean active;
}