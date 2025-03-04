package com.dls.loan.domain.application.service.mapper;

import com.dls.common.domain.valueobject.*;
import com.dls.loan.domain.application.service.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.domain.application.service.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.domain.core.entity.LoanDisbursement;
import com.dls.loan.domain.core.valueobject.TrackingId;

import java.time.LocalDate;

public class LoanDisbursementDataMapper {

    public LoanDisbursement disbursementLoanCommandToDisbursement(DisbursementLoanCommand disbursementLoanCommand) {
        return LoanDisbursement.builder()
                .disbursementDate(LocalDate.parse(disbursementLoanCommand.getDisbursementDate()))
                .trackingId(new TrackingId(disbursementLoanCommand.getTrackingId()))
                .disbursementAmount(new Money(disbursementLoanCommand.getDisbursementAmount()))
                .customerId(new CustomerId(disbursementLoanCommand.getCustomerId()))
                .maturityDate(LocalDate.parse(disbursementLoanCommand.getMaturityDate()))
                .currency(Currency.valueOf(disbursementLoanCommand.getCurrency()))
                .repaymentMethod(RepaymentMethod.valueOf(disbursementLoanCommand.getRepaymentMethod()))
                .interestRate(new InterestRate(disbursementLoanCommand.getInterestRate()))
                .repaymentCycle(RepaymentCycle.valueOf(disbursementLoanCommand.getRepaymentCycle()))
                .totalTerms(disbursementLoanCommand.getTotalTerms())
                .gracePeriod(disbursementLoanCommand.getGracePeriod())
                .paymentCardNo(disbursementLoanCommand.getPaymentCardNo())
                .autoDeductionCardNo(disbursementLoanCommand.getAutoDeductionCardNo())
                .disbursementAmount(new Money(disbursementLoanCommand.getDisbursementAmount()))
                .build();
    }

    public DisbursementLoanResponse loanDisbursementToDisbursementLoanResponse(LoanDisbursement loanDisbursement) {
        return DisbursementLoanResponse.builder()
                .trackingId(loanDisbursement.getTrackingId().getValue())
                .accountId(loanDisbursement.getLoanId().getValue())
                .build();
    }

}
