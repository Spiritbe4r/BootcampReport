package com.bootcamp.report.models.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

  private String accountNumber;

  private String typeOfAccount;

  private double amount;

  private String currency;

  private int movementPerMonth;

  private int maxLimitMovementPerMonth;


}
