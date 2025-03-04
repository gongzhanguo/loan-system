package com.dls.loan.domain.application.service.dto.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DisbursementLoanCommand {

    String disbursementDate;
    UUID trackingId;
    UUID productId;
    BigDecimal disbursementAmount;
    UUID customerId;
    String maturityDate;
    String currency;
    String repaymentMethod;
    BigDecimal interestRate;
    String repaymentCycle;
    Integer totalTerms;
    Integer gracePeriod;
    String paymentCardNo;
    String autoDeductionCardNo;
}
