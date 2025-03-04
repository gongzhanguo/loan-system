package com.dls.loan.domain.core.repaymentschedule;

import com.dls.common.domain.valueobject.Money;
import com.dls.common.domain.valueobject.ScheduleStatus;
import com.dls.loan.domain.core.entity.LoanAccount;
import com.dls.loan.domain.core.entity.RepaymentSchedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EqualInstallmentStrategy {

    public static List<RepaymentSchedule> generateRepaymentPlan(LoanAccount loanAccount) {
        List<RepaymentSchedule> repaymentSchedules = new ArrayList<>();
        BigDecimal monthlyInterestRate = loanAccount.getInterestRate().getRate()
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        Integer totalTerms = loanAccount.getTotalTerms();
        BigDecimal principleAmount = loanAccount.getPrincipleAmount().getAmount();
        // 等额本息计算公式
        BigDecimal factor = monthlyInterestRate.add(BigDecimal.ONE)
                .pow(totalTerms)
                .subtract(BigDecimal.ONE);

        BigDecimal monthlyPayment = principleAmount
                .multiply(monthlyInterestRate)
                .multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(totalTerms))
                .divide(factor, 2, RoundingMode.HALF_UP);

        BigDecimal remainingBalance = principleAmount;

        for (int term = 1; term <= totalTerms; term++) {
            BigDecimal interestAmount = remainingBalance.multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal principal = monthlyPayment.subtract(interestAmount);
            remainingBalance = remainingBalance.subtract(principal);

            repaymentSchedules.add(RepaymentSchedule.builder()
                    .loanId(loanAccount.getLoanId())
                    .interestAmount(new Money(interestAmount))
                    .principleAmount(new Money(principal))
                    .periodNo(term)
                    .scheduleStatus(ScheduleStatus.NORMAL)
                    .build()
            );
        }
        return repaymentSchedules;
    }
}
