package com.dls.loan.domain.core.service.repaymentschedule;

import com.dls.loan.domain.core.enums.ScheduleStatus;
import com.dls.loan.domain.core.entity.LoanRepaymentScheduleEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EqualPrincipalStrategy {

    public static List<LoanRepaymentScheduleEntity> generateRepaymentPlan(Integer totalTerms, BigDecimal principleAmount,
                                                                          BigDecimal yearInterestRate) {
        List<LoanRepaymentScheduleEntity> loanRepaymentScheduleEntities = new ArrayList<>();
        BigDecimal monthlyInterestRate = yearInterestRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal principalPerMonth = principleAmount
                .divide(BigDecimal.valueOf(totalTerms), 2, RoundingMode.HALF_UP);

        BigDecimal remainingBalance = principleAmount;

        for (int term = 1; term <= totalTerms; term++) {
            BigDecimal interestAmount = remainingBalance.multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalPayment = principalPerMonth.add(interestAmount);
            remainingBalance = remainingBalance.subtract(principalPerMonth);

            loanRepaymentScheduleEntities.add(LoanRepaymentScheduleEntity.builder()
                    .interestAmount(interestAmount)
                    .principleAmount(totalPayment.subtract(interestAmount))
                    .periodNo(term)
                    .scheduleStatus(ScheduleStatus.NORMAL)
                    .build()
            );
        }
        return loanRepaymentScheduleEntities;
    }
}
