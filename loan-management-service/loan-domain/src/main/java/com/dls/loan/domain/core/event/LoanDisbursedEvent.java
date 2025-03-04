package com.dls.loan.domain.core.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class LoanDisbursedEvent extends DomainEvent {

    private String trackingId;
    private String loanId;
    private String productId;
    private String customerId;
    private BigDecimal disbursementAmount;
    private String customerName;
    private String identityNo;
    private String paymentAccountNo;
    private String paymentBankName;
    private String paymentBankNo;

}
