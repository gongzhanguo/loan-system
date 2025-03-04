package com.dls.loan.interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.dls.loan.infrastructure.repository"})
@EntityScan(basePackages = {"com.dls.loan.domain.core.entity"})
@SpringBootApplication(scanBasePackages = "com.dls.loan")
public class LoanServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(LoanServiceApplication.class, args);
    }
}
