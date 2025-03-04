package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.enums.ScheduleStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 还款计划
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_repayment_schedule")
@Entity
public class RepaymentSchedule extends BaseDomainEntity<RepaymentSchedule> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanAccount loanAccount;
    private Integer periodNo;
    private BigDecimal principleAmount;
    private BigDecimal interestAmount;
    private BigDecimal penaltyAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate clearDate;
    private BigDecimal repayPrincipleAmount;
    private BigDecimal repayInterestAmount;
    private BigDecimal repayPenaltyAmount;
    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;

    public void initAndValidateRepaymentSchedule(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }

    @Override
    public boolean sameIdentityAs(RepaymentSchedule other) {
        return other != null && id.equals(other.id)
                && loanAccount.getLoanId().equals(other.getLoanAccount().getLoanId());
    }
}
