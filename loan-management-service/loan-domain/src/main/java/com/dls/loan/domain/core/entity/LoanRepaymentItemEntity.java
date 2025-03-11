package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.entity.base.BaseDomainEntity;
import com.dls.loan.domain.core.enums.RepaymentEventType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_repayment")
@Entity
public class LoanRepaymentItemEntity extends BaseDomainEntity<LoanRepaymentItemEntity> {

    private String loanId;
    private LocalDate repaymentDate;
    private String trackingId;
    private Integer termNo;
    private BigDecimal principleAmount;
    private BigDecimal interestAmount;
    private BigDecimal penaltyAmount;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private RepaymentEventType repaymentEventType;

    @Override
    protected boolean sameIdentityAs(LoanRepaymentItemEntity other) {
        return other!= null && trackingId.equals(other.trackingId);
    }
}
