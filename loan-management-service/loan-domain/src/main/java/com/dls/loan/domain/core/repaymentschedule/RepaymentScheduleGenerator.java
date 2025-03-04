package com.dls.loan.domain.core.repaymentschedule;

import com.dls.loan.domain.core.enums.RepaymentMethod;
import com.dls.loan.domain.core.entity.LoanRepaymentScheduleEntity;
import com.dls.loan.domain.core.exception.LoanDomainException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class RepaymentScheduleGenerator {

    public static List<LoanRepaymentScheduleEntity> calculate(Integer totalTerms, BigDecimal principleAmount,
                                                              BigDecimal monthlyInterestRate, RepaymentMethod repaymentMethod) {
        switch (repaymentMethod) {
            case EQUAL_INSTALLMENT:
                return EqualInstallmentStrategy.generateRepaymentPlan(totalTerms, principleAmount, monthlyInterestRate);
            case EQUAL_PRINCIPAL:
                return EqualPrincipalStrategy.generateRepaymentPlan(totalTerms, principleAmount, monthlyInterestRate);
            default:
                throw new LoanDomainException("不支持的还款还款方式");
        }
    }

}
