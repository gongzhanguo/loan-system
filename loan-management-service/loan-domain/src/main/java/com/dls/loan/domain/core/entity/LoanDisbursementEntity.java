package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.entity.base.BaseDomainEntity;
import com.dls.loan.domain.core.enums.*;
import com.dls.loan.domain.core.exception.LoanDomainException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_disbursement")
@Entity
public class LoanDisbursementEntity extends BaseDomainEntity<LoanDisbursementEntity> {

    @Id
    private String trackingId;
    @JoinColumn(name = "loan_id")
    @OneToOne(cascade = CascadeType.PERSIST)
    private LoanAccountEntity loanAccount;
    private String productId;
    private LocalDate disbursementDate;
    private BigDecimal disbursementAmount;
    private String customerId;
    private LocalDate maturityDate;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private RepaymentMethod repaymentMethod;
    private BigDecimal interestRate;
    private BigDecimal overdueInterestRate;
    @Enumerated(EnumType.STRING)
    private RepaymentCycle repaymentCycle;
    private Integer totalTerms;
    private Integer gracePeriod;
    @Enumerated(EnumType.STRING)
    private DisbursementStatus disbursementStatus;
    @Enumerated(EnumType.STRING)
    private DisburseFailType disburseFailType;
    private String paymentAccountNo;
    private String paymentBankName;
    private String paymentBankNo;

    public void validate() {
        validateDisbursementAmount();
        validateProduct();
    }

    public void initializer() {
        this.disbursementStatus = DisbursementStatus.PROCESSING;
    }

    public void disbursed() {
        this.disbursementStatus = DisbursementStatus.SUCCESS;
        loanAccount.disbursed();
    }

    public void cancel(DisburseFailType disburseFailType) {
        this.disbursementStatus = DisbursementStatus.CANCEL;
        this.disburseFailType = disburseFailType;
        loanAccount.cancel();
    }

    public void reversal() {
        this.disbursementStatus = DisbursementStatus.REVERSAL;
    }

    /**
     * TODO 产品限额检查
     * 产品限额检查，包括利率上限、放款金额上限
     */
    private void validateProduct() {

    }

    private void validateDisbursementAmount() {
        if (disbursementAmount == null || disbursementAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanDomainException("贷款放款金额必须大于0");
        }
    }

    @Override
    public boolean sameIdentityAs(LoanDisbursementEntity other) {
        return other!= null && trackingId.equals(other.trackingId);
    }
}
