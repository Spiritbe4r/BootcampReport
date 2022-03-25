package com.bootcamp.report.models.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
public class AccountDto {

  private String accountNumber;

  private String typeOfAccount;

  private double amount;

  private String currency;

  private int movementPerMonth;

  private int maxLimitMovementPerMonth;


}
