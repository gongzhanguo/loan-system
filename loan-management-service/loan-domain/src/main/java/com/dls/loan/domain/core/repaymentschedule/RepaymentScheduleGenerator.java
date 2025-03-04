package com.dls.loan.domain.core.repaymentschedule;

import com.dls.loan.domain.core.entity.LoanAccount;
import com.dls.loan.domain.core.entity.RepaymentSchedule;
import com.dls.loan.domain.core.exception.LoanDomainException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RepaymentScheduleGenerator {

    public static List<RepaymentSchedule> calculate(LoanAccount loanAccount) {
        switch (loanAccount.getRepaymentMethod()) {
            case EQUAL_INSTALLMENT:
                return EqualInstallmentStrategy.generateRepaymentPlan(loanAccount);
            case EQUAL_PRINCIPAL:
                return EqualPrincipalStrategy.generateRepaymentPlan(loanAccount);
            default:
                throw new LoanDomainException("不支持的还款还款方式");
        }
    }

}
