package com.bootcamp.report.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount {
  private String typeOfAccount;

  private String accountNumber;

  private double amount;

  private int maxLimitMovementPerMonth;

  private int movementPerMonth;
}