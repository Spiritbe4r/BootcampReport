package com.bootcamp.report.config;

import com.bootcamp.report.handlers.ProductReportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductReportRouter {

  /**
   * Routes router function.
   *
   * @param reportHandler the report handler
   * @return the router function
   */
  @Bean
  public RouterFunction<ServerResponse> routes(ProductReportHandler reportHandler) {
    return route(GET("/api/report/client/{clientIdNumber}"), reportHandler::generateClientReport);

  }
}
