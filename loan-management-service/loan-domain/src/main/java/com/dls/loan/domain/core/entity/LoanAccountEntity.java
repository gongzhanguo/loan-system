package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.enums.*;
import com.dls.loan.domain.core.exception.LoanDomainException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_account")
@Entity
public class LoanAccount extends BaseDomainEntity<LoanAccount> {

    @Id
    private String loanId;
    private String customerId;
    private String productId;
    private LocalDate disbursementDate;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
    @Enumerated(EnumType.STRING)
    private LoanForm loanForm;
    @Enumerated(EnumType.STRING)
    private RepaymentMethod repaymentMethod;
    private BigDecimal interestRate;
    private LocalDate maturityDate;
    private LocalDate originalMaturityDate;
    @Enumerated(EnumType.STRING)
    private RepaymentCycle repaymentCycle;
    private Integer totalTerms;
    private Integer gracePeriod;
    private BigDecimal principleAmount;
    private BigDecimal interestAmount;
    private BigDecimal penaltyAmount;
    private BigDecimal principleBalance;
    private BigDecimal overduePrinciple;
    private BigDecimal overdueInterestRate;
    private LocalDate clearDate;
    private BigDecimal dailyAccrualInterest;
    private Integer totalOverdueTerms;
    private Integer currentOverdueTerm;
    private Integer currentOverdueDays;
    private LocalDate lastRepaymentDate;

    @OneToMany(mappedBy = "loanAccount", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "loanAccount", cascade = CascadeType.ALL)
    private List<RepaymentSchedule> repaymentSchedules;

    public void initAndValidateLoanAccount() {
        initialize();

        validate();
    }

    private void initialize() {
        loanStatus = LoanStatus.PROCESSING;
        loanForm = LoanForm.NORMAL;
        interestAmount = BigDecimal.ZERO;
        penaltyAmount = BigDecimal.ZERO;
        overduePrinciple = BigDecimal.ZERO;

        initializeRepaymentSchedule();

        initializeBankAccount();
    }

    private void initializeRepaymentSchedule() {
        repaymentSchedules.stream().forEach(repaymentSchedule -> {
            repaymentSchedule.initAndValidateRepaymentSchedule(this);
        });
    }

    private void initializeBankAccount() {
        bankAccounts.stream().forEach(bankAccount -> {
            bankAccount.initAndValidateBankAccount(this);
        });
    }

    private void validate() {
        if (loanId == null) {
            throw new LoanDomainException("借据号不能为空");
        }
        if (principleAmount == null || principleAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanDomainException("贷款本金金额必须大于0");
        }
        if (interestRate == null || interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanDomainException("贷款利率必须大于0");
        }
        if (overdueInterestRate == null || overdueInterestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new LoanDomainException("贷款逾期利率必须大于0");
        }
        if (gracePeriod < 0) {
            throw new LoanDomainException("宽限期天数必须大于0");
        }
        if (bankAccounts == null || bankAccounts.isEmpty()) {
            throw new LoanDomainException("银行账户不能为空");
        }
        validateRepaymentSchedule();

        validateBankAccount();
    }

    private void validateRepaymentSchedule() {
        BigDecimal totalPrincipleAmount = repaymentSchedules.stream().map(RepaymentSchedule::getPrincipleAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPrincipleAmount.compareTo(principleAmount) != 0) {
//            throw new LoanDomainException("还款计划生成异常，还款计划本金总额和贷款金额不相等");
        }
    }

    private void validateBankAccount() {
        bankAccounts.stream().filter(bankAccount -> bankAccount.getBankAccountType() == BankAccountType.PAYMENT)
                .findFirst().orElseThrow(() -> new LoanDomainException("贷款账户信息中不存在支付账户"));

        bankAccounts.stream().filter(bankAccount -> bankAccount.getBankAccountType() == BankAccountType.AUTO_DEDUCTION)
                .findFirst().orElseThrow(() -> new LoanDomainException("贷款账户信息中不存在扣款账户"));
    }

    public void disbursed() {
        this.loanStatus = LoanStatus.DISBURSED;
    }

    public void reversal() {
        this.loanStatus = LoanStatus.REVERSAL;
    }

    @Override
    public boolean sameIdentityAs(LoanAccount other) {
        return other != null && loanId.equals(other.loanId);
    }
}
