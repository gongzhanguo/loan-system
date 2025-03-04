package com.dls.loan.domain.core.repaymentschedule;

import com.dls.common.domain.valueobject.Money;
import com.dls.common.domain.valueobject.ScheduleStatus;
import com.dls.loan.domain.core.entity.LoanAccount;
import com.dls.loan.domain.core.entity.RepaymentSchedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EqualPrincipalStrategy {

    public static List<RepaymentSchedule> generateRepaymentPlan(LoanAccount loanAccount) {
        List<RepaymentSchedule> repaymentSchedules = new ArrayList<>();
        BigDecimal monthlyInterestRate = loanAccount.getInterestRate().getRate()
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        Integer totalTerms = loanAccount.getTotalTerms();
        BigDecimal principleAmount = loanAccount.getPrincipleAmount().getAmount();
        BigDecimal principalPerMonth = principleAmount
                .divide(BigDecimal.valueOf(totalTerms), 2, RoundingMode.HALF_UP);

        BigDecimal remainingBalance = principleAmount;

        for (int term = 1; term <= totalTerms; term++) {
            BigDecimal interestAmount = remainingBalance.multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalPayment = principalPerMonth.add(interestAmount);
            remainingBalance = remainingBalance.subtract(principalPerMonth);

            repaymentSchedules.add(RepaymentSchedule.builder()
                    .loanId(loanAccount.getLoanId())
                    .interestAmount(new Money(interestAmount))
                    .principleAmount(new Money(totalPayment.subtract(interestAmount)))
                    .periodNo(term)
                    .scheduleStatus(ScheduleStatus.NORMAL)
                    .build()
            );
        }
        return repaymentSchedules;
    }
}
