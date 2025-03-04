package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.enums.BankAccountType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_bank_account")
@Entity
public class BankAccount extends BaseDomainEntity<BankAccount> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanAccount loanAccount;

    private String customerName;
    private String identityNo;
    private String accountNo;
    private String bankName;
    private String bankNo;
    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @Override
    boolean sameIdentityAs(BankAccount other) {
        return other != null && id.equals(other.id)
                && loanAccount.getLoanId().equals(other.getLoanAccount().getLoanId());
    }

    public void initAndValidateBankAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }
}
