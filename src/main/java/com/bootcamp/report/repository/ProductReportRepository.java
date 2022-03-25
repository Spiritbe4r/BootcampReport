package com.bootcamp.report.repository;

import com.bootcamp.report.models.entity.ProductReport;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

@Configuration
public interface ProductReportRepository extends ReactiveMongoRepository<ProductReport, String> {


}
