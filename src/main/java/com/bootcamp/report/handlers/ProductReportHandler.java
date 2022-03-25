package com.bootcamp.report.handlers;

import com.bootcamp.report.service.ProductReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductReportHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductReportHandler.class);
  @Autowired
  private ProductReportService accountService;


}
