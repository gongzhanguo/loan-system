package com.dls.loan.application.mapper;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.domain.core.enums.Currency;
import com.dls.loan.domain.core.enums.RepaymentCycle;
import com.dls.loan.domain.core.enums.RepaymentMethod;
import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanDisbursementDataMapper {

    public LoanDisbursementEntity disbursementLoanCommandToDisbursement(DisbursementLoanCommand disbursementLoanCommand) {
        return LoanDisbursementEntity.builder()
                .disbursementDate(LocalDate.parse(disbursementLoanCommand.getDisbursementDate()))
                .trackingId(disbursementLoanCommand.getTrackingId())
                .disbursementAmount(disbursementLoanCommand.getDisbursementAmount())
                .customerId(disbursementLoanCommand.getCustomerId())
                .productId(disbursementLoanCommand.getProductId())
                .maturityDate(LocalDate.parse(disbursementLoanCommand.getMaturityDate()))
                .currency(Currency.valueOf(disbursementLoanCommand.getCurrency()))
                .repaymentMethod(RepaymentMethod.valueOf(disbursementLoanCommand.getRepaymentMethod()))
                .interestRate(disbursementLoanCommand.getInterestRate())
                .overdueInterestRate(disbursementLoanCommand.getOverdueInterestRate())
                .repaymentCycle(RepaymentCycle.valueOf(disbursementLoanCommand.getRepaymentCycle()))
                .totalTerms(disbursementLoanCommand.getTotalTerms())
                .gracePeriod(disbursementLoanCommand.getGracePeriod())
                .disbursementAmount(disbursementLoanCommand.getDisbursementAmount())
                .paymentAccountNo(disbursementLoanCommand.getPaymentBankAccount().getAccountNo())
                .paymentBankName(disbursementLoanCommand.getPaymentBankAccount().getBankName())
                .paymentBankNo(disbursementLoanCommand.getPaymentBankAccount().getBankNo())
                .build();
    }

    public DisbursementLoanResponse loanDisbursementToDisbursementLoanResponse(LoanDisbursementEntity loanDisbursementEntity) {
        return DisbursementLoanResponse.builder()
                .trackingId(loanDisbursementEntity.getTrackingId())
                .loanId(loanDisbursementEntity.getLoanAccount().getLoanId())
                .disbursementStatus(loanDisbursementEntity.getDisbursementStatus())
                .build();
    }

}
