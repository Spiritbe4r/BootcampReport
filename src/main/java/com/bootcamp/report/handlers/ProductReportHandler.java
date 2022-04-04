package com.bootcamp.report.handlers;

import com.bootcamp.report.models.dto.ClientDTO;
import com.bootcamp.report.models.entity.ProductReport;
import com.bootcamp.report.service.ProductReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductReportHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductReportHandler.class);

  private final ProductReportService productReportService;

  public ProductReportHandler(ProductReportService productReportService){
    this.productReportService=productReportService;
  }

  public Mono<ServerResponse> generateClientReport(ServerRequest request) {
    ProductReport report = new ProductReport();
    String customerIdentityNumber = request.pathVariable("clientIdNumber");
    return Mono.just(report)
          .flatMap(productreport -> productReportService.getClient(customerIdentityNumber)
                .flatMap(customerRequest -> {
                  productreport.setClient(
                        ClientDTO
                              .builder()
                              .name(customerRequest.getName())
                              .clientIdNumber(customerRequest.getClientIdNumber())
                              .code(customerRequest.getClientType().getCode())
                              .build());
                  return productReportService.getAccount(customerIdentityNumber);
                })
                .flatMap(Account -> {
                  if(Account != null){
                    productreport.getProducts().add(Account);
                  }
                  return productReportService.getFixedTermAccount(customerIdentityNumber);
                })
                .flatMap(fixedTermAccount -> {
                  if(fixedTermAccount != null){
                    productreport.getProducts().add(fixedTermAccount);
                  }
                  return productReportService.getCurrentAccount(customerIdentityNumber).collectList();
                })
                .flatMap(currentAccount -> {
                  if(currentAccount.get(0).getAccountNumber()!=null){
                    productreport.getProducts().add(currentAccount);
                  }
                  return productReportService.getCreditCard(customerIdentityNumber);
                })
                .flatMap(creditcard -> {
                  if(creditcard.getPan() != null){
                    creditcard.setTypeOfAccount("CREDITCARD");
                    productreport.getProducts().add(creditcard);
                  }
                  return productReportService.getCredit(customerIdentityNumber).collectList();
                })
                .flatMap(credit -> {
                  if(credit.get(0).getContractNumber() == null){
                    credit.stream().forEach(c->{
                      c.setTypeOfAccount("CREDIT");
                    });
                    productreport.getProducts().add(credit);
                  }
                  return Mono.just(productreport);
                }))
          .flatMap(c-> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
          .switchIfEmpty(ServerResponse.notFound().build());
  }


}
