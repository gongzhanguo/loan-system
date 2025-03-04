package com.dls.loan.domain.core;

import com.dls.loan.domain.core.enums.LoanForm;
import com.dls.loan.domain.core.enums.LoanStatus;
import com.dls.loan.domain.core.entity.BankAccountEntity;
import com.dls.loan.domain.core.entity.LoanAccountEntity;
import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.entity.LoanRepaymentScheduleEntity;
import com.dls.loan.domain.core.event.LoanDisbursedEvent;
import com.dls.loan.domain.core.repaymentschedule.RepaymentScheduleGenerator;
import com.dls.loan.domain.core.valueobject.LoanIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    private final LoanIdGenerator loanIdGenerator;

    public LoanDisbursementServiceImpl(LoanIdGenerator loanIdGenerator) {
        this.loanIdGenerator = loanIdGenerator;
    }


    @Override
    public LoanDisbursedEvent disburse(LoanDisbursementEntity loanDisbursementEntity, List<BankAccountEntity> bankAccountEntities) {
        loanDisbursementEntity.validate();
        loanDisbursementEntity.initializer();
        LoanAccountEntity loanAccount = openAccount(loanDisbursementEntity, bankAccountEntities);
        loanDisbursementEntity.setLoanAccount(loanAccount);
        return LoanDisbursedEvent.builder()
                .loanId(loanDisbursementEntity.getLoanAccount().getLoanId())
                .customerId(loanDisbursementEntity.getCustomerId())
                .productId(loanDisbursementEntity.getProductId())
                .trackingId(loanDisbursementEntity.getTrackingId())
                .disbursementAmount(loanDisbursementEntity.getDisbursementAmount())
                .paymentAccountNo(loanDisbursementEntity.getPaymentAccountNo())
                .paymentBankName(loanDisbursementEntity.getPaymentBankName())
                .paymentBankNo(loanDisbursementEntity.getPaymentBankNo())
                .build();
    }

    private LoanAccountEntity openAccount(LoanDisbursementEntity loanDisbursementEntity, List<BankAccountEntity> bankAccountEntities) {
        List<LoanRepaymentScheduleEntity> loanRepaymentScheduleEntities = RepaymentScheduleGenerator.calculate(loanDisbursementEntity.getTotalTerms(),
                loanDisbursementEntity.getDisbursementAmount(),
                loanDisbursementEntity.getInterestRate(),
                loanDisbursementEntity.getRepaymentMethod());

        LoanAccountEntity loanAccountEntity = LoanAccountEntity.builder()
                .loanId(loanIdGenerator.generate())
                .customerId(loanDisbursementEntity.getCustomerId())
                .productId(loanDisbursementEntity.getProductId())
                .disbursementDate(loanDisbursementEntity.getDisbursementDate())
                .currency(loanDisbursementEntity.getCurrency())
                .loanStatus(LoanStatus.PROCESSING)
                .loanForm(LoanForm.NORMAL)
                .repaymentMethod(loanDisbursementEntity.getRepaymentMethod())
                .interestRate(loanDisbursementEntity.getInterestRate())
                .maturityDate(loanDisbursementEntity.getMaturityDate())
                .originalMaturityDate(loanDisbursementEntity.getMaturityDate())
                .repaymentCycle(loanDisbursementEntity.getRepaymentCycle())
                .totalTerms(loanDisbursementEntity.getTotalTerms())
                .gracePeriod(loanDisbursementEntity.getGracePeriod())
                .principleAmount(loanDisbursementEntity.getDisbursementAmount())
                .interestAmount(BigDecimal.ZERO)
                .penaltyAmount(BigDecimal.ZERO)
                .principleBalance(loanDisbursementEntity.getDisbursementAmount())
                .overduePrinciple(BigDecimal.ZERO)
                .overdueInterestRate(loanDisbursementEntity.getOverdueInterestRate())
                .loanRepaymentScheduleEntities(loanRepaymentScheduleEntities)
                .bankAccountEntities(bankAccountEntities)
                .build();

        loanAccountEntity.initAndValidateLoanAccount();
        return loanAccountEntity;
    }

    @Override
    public void disbursed(LoanDisbursementEntity loanDisbursementEntity) {
        loanDisbursementEntity.disbursed();
    }

    @Override
    public void reversal(LoanDisbursementEntity loanDisbursementEntity) {
        loanDisbursementEntity.reversal();
    }

}
