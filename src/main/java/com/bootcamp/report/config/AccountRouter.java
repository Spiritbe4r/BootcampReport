package com.bootcamp.report.config;

import com.bootcamp.report.handlers.ProductReportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

  /**
   * Routes router function.
   *
   * @param accountHandler the account handler
   * @return the router function
   */
  /*@Bean
  public RouterFunction<ServerResponse> routes(ProductReportHandler accountHandler) {
    return null;//route(GET("/api/account"), accountHandler::findAll);

  }*/
}
