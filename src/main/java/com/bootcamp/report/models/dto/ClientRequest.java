package com.bootcamp.report.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
  private String name;
  private String clientIdNumber;
  private String clientIdType;
  private ClientTypeRequest clientType;
}