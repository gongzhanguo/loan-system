package com.dls.loan.domain.core.repaymentschedule;

import com.dls.loan.domain.core.enums.ScheduleStatus;
import com.dls.loan.domain.core.entity.LoanRepaymentScheduleEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EqualInstallmentStrategy {

    public static List<LoanRepaymentScheduleEntity> generateRepaymentPlan(Integer totalTerms, BigDecimal principleAmount,
                                                                          BigDecimal yearInterestRate) {
        List<LoanRepaymentScheduleEntity> loanRepaymentScheduleEntities = new ArrayList<>();
        BigDecimal monthlyInterestRate = yearInterestRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

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

            loanRepaymentScheduleEntities.add(LoanRepaymentScheduleEntity.builder()
                    .interestAmount(interestAmount)
                    .principleAmount(principal)
                    .periodNo(term)
                    .scheduleStatus(ScheduleStatus.NORMAL)
                    .build()
            );
        }
        return loanRepaymentScheduleEntities;
    }
}
