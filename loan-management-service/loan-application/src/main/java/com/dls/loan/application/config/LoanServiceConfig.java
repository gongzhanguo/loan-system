package com.dls.loan.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "loan-service")
public class LoanServiceConfig {

    private String loanDisbursementTopicName;
    private String loanDisbursedTopicName;

}
