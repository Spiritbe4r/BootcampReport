package com.bootcamp.report.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

  private String typeOfAccount;

  private String contractNumber;

  private double amountInitial;

  private double amount;

  private boolean debitor;
}
