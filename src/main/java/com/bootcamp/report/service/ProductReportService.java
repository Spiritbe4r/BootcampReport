package com.bootcamp.report.service;


import com.bootcamp.report.models.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReportService {

  Mono<ClientRequest> getClient(String clientIdNumber);
  Flux<Credit> getCredit(String clientIdNumber);
  Mono<CreditCard> getCreditCard(String clientIdNumber);
  Mono<Account> getAccount(String clientIdNumber);
  Flux<Current> getCurrentAccount(String clientIdNumber);
  Mono<FixedTermAccount> getFixedTermAccount(String clientIdNumber);



}
