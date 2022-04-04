package com.bootcamp.report.service.impl;


import com.bootcamp.report.models.dto.*;
import com.bootcamp.report.service.ProductReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${microservices-urls.api-currentAcc}")
  private String apiCurrentaccount;

  @Value("${microservices-urls.api-fixedtermsAcc}")
  private String apiFixedTermsaccount;

  @Override
  public Mono<ClientRequest> getClient(String clientIdNumber) {
    Map<String, Object> params = new HashMap<>();
    LOGGER.info("initializing client query");
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiClient)
          .build()
          .get()
          .uri("/findClientCredit/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
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
  public Mono<Account> getAccount(String clientIdNumber) {
    Map<String,Object>params=new HashMap<>();
    LOGGER.info("Searching CreditCard by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiAccount)
          .build()
          .get()
          .uri("/client/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Account.class))
          .switchIfEmpty(Mono.just(Account.builder().accountNumber(null).build()))
          .doOnNext(c -> LOGGER.info("Account Response: Account={}", c.getAccountNumber()));

  }

  @Override
  public Flux<Current> getCurrentAccount(String clientIdNumber) {
    Map<String, Object> params = new HashMap<>();
    LOGGER.info("Searching CurrentAccount by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiCurrentaccount)
          .build()
          .get()
          .uri("/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Current.class))
          .switchIfEmpty(Mono.just(Current.builder().accountNumber(null).build()))
          .doOnNext(c -> LOGGER.info("CurrentAccount Response: CurrentAccount={}", c.getAccountNumber()));
  }

  @Override
  public Mono<FixedTermAccount> getFixedTermAccount(String clientIdNumber) {
    Map<String, Object> params = new HashMap<>();
    LOGGER.info("Searching FixedTermAccount by: {}" + clientIdNumber);
    params.put("clientIdNumber", clientIdNumber);
    return webClientBuilder.baseUrl(apiFixedTermsaccount)
          .build()
          .get()
          .uri("/{clientIdNumber}", clientIdNumber)
          .accept(MediaType.APPLICATION_JSON)
          .exchangeToMono(clientResponse -> clientResponse.bodyToMono(FixedTermAccount.class))
          .switchIfEmpty(Mono.just(FixedTermAccount.builder().accountNumber(null).build()))
          .doOnNext(c -> LOGGER.info("FixedTermAccount Response: FixedTermAccount={}", c.getAccountNumber()));
  }


}
