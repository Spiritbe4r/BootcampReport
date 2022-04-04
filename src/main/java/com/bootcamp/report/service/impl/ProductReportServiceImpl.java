package com.bootcamp.report.service.impl;


import com.bootcamp.report.models.dto.*;
import com.bootcamp.report.models.entity.ProductReport;
import com.bootcamp.report.repository.ProductReportRepository;
import com.bootcamp.report.service.ProductReportService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class ProductReportServiceImpl implements ProductReportService{

  @Autowired
  private WebClient.Builder webClientBuilder;

  private  static final Logger LOGGER =LoggerFactory.getLogger(ProductReportServiceImpl.class);

  @Value("${microservices-urls.api-client}")
  private String apiClient;

  @Value("${microservices-urls.api-credit}")
  private String apiCredit;
  @Value("${microservices-urls.api-creditcard}")
  private String apiCreditcard;

  @Value("${microservices-urls.api-account}")
  private String apiAccount;

  @Override
  public Mono<ClientRequest> getClient(String clientIdNumber) {
    return webClientBuilder.baseUrl(apiClient)
          .build()
          .get()
          .uri("/findClientCredit/{clientIdNumber}")
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(ClientRequest.class))
          .doOnNext(c->LOGGER.info("Client "+ c.getName()));
  }

  @Override
  public Flux<Credit> getCredit(String clientIdNumber) {
    Map<String, Object> params = new HashMap<>();
    LOGGER.info("Searching Credit by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiCredit)
          .build()
          .get()
          .uri("/client/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Credit.class))
          .switchIfEmpty(Mono.just(Credit.builder().contractNumber(null).build()))
          .doOnNext(c -> LOGGER.info("Credit Response: Credit={}", c.getContractNumber()));
  }

  @Override
  public Mono<CreditCard> getCreditCard(String clientIdNumber) {
     Map<String,Object>params=new HashMap<>();
    LOGGER.info("Searching CreditCard by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiCreditcard)
          .build()
          .get()
          .uri("/client/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CreditCard.class))
          .switchIfEmpty(Mono.just(CreditCard.builder().pan(null).build()))
          .doOnNext(c -> LOGGER.info("CreditCard Response: CreditCard={}", c.getPan()));

  }

  @Override
  public Mono<AccountDto> getAccount(String clientIdNumber) {
    Map<String,Object>params=new HashMap<>();
    LOGGER.info("Searching CreditCard by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiAccount)
          .build()
          .get()
          .uri("/client/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(AccountDto.class))
          .switchIfEmpty(Mono.just(AccountDto.builder().accountNumber(null).build()))
          .doOnNext(c -> LOGGER.info("Account Response: Account={}", c.getAccountNumber()));

  }

  @Override
  public Flux<CurrentAccount> getCurrentAccount(String clientIdNumber) {
    return null;
  }

  @Override
  public Mono<FixedTermAccount> getFixedTermAccount(String clientIdNumber) {
    return null;
  }


}
