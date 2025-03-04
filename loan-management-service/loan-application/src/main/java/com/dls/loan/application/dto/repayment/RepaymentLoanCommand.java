package com.dls.loan.application.dto.repayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class RepaymentLoanCommand {
    private String trackingId;
    private String loanId;
    private Integer termNo;
    private BigDecimal principleAmount;
    private BigDecimal interestAmount;
    private BigDecimal penaltyAmount;
    private BigDecimal totalAmount;
}
