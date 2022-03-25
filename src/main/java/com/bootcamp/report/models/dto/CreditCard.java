package com.bootcamp.report.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {


  private String pan;

  private double creditLimit;

  private double totalConsumption;

  private double balanceAmount;

  private boolean debitor;
}
