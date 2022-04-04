package com.bootcamp.report.handlers;

import com.bootcamp.report.models.dto.ClientDTO;
import com.bootcamp.report.models.entity.ProductReport;
import com.bootcamp.report.service.ProductReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductReportHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductReportHandler.class);
  @Autowired
  private ProductReportService productReportService;

  public Mono<ServerResponse> generateClientReport(final ServerRequest request){
    ProductReport report=new ProductReport();

    String clientIdNumber=request.pathVariable("clientIdNumber");
    LOGGER.info("Client id Number: " + clientIdNumber);
    return Mono.just(report)
          .flatMap(productReport -> productReportService.getClient(clientIdNumber)

                .flatMap(clientRequest -> {
                  productReport.setClient(
                        ClientDTO.builder()
                              .name(clientRequest.getName())
                              .clientIdNumber(clientRequest.getClientIdNumber())
                              .code(clientRequest.getClientType().getCode())
                              .build());
                  LOGGER.info(" The Client : " ,clientRequest.getName());
                  return productReportService.getAccount(clientIdNumber);
              })

                .flatMap(savingAccount -> {
                  if(!savingAccount.equals(null)){
                    productReport.getProducts().add(savingAccount);
                  }
                  return productReportService.getFixedTermAccount(clientIdNumber);
                })
                .flatMap(fixedTermAccount -> {
                  if(!fixedTermAccount.equals(null)){
                    productReport.getProducts().add(fixedTermAccount);
                  }
                  return productReportService.getCurrentAccount(clientIdNumber).collectList();
                })
                .flatMap(currentAccount -> {
                  if(currentAccount.get(0).getAccountNumber()!=null){
                  //if(currentAccount.getAccountNumber()!=null){
                    productReport.getProducts().add(currentAccount);
                  }
                  return productReportService.getCreditCard(clientIdNumber);
                })
                .flatMap(creditcard -> {
                  if(!creditcard.getPan().equals(null)){
                    creditcard.setTypeOfAccount("CREDITCARD");
                    productReport.getProducts().add(creditcard);
                  }
                  return productReportService.getCredit(clientIdNumber).collectList();
                })
                .flatMap(credit -> {
                  if(credit.get(0).getContractNumber().equals(null)){
                    credit.stream().forEach(c->{
                      c.setTypeOfAccount("CREDIT");
                    });
                    productReport.getProducts().add(credit);
                  }
                  return Mono.just(productReport);
                }))
          .flatMap(c-> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
          .switchIfEmpty(ServerResponse.notFound().build());


  }


}
