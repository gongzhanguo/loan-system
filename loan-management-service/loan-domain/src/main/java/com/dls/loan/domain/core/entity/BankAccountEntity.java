package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.entity.base.BaseDomainEntity;
import com.dls.loan.domain.core.enums.BankAccountType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_bank_account")
@Entity
public class BankAccountEntity extends BaseDomainEntity<BankAccountEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanAccountEntity loanAccount;

    private String customerName;
    private String identityNo;
    private String accountNo;
    private String bankName;
    private String bankNo;
    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @Override
    public boolean sameIdentityAs(BankAccountEntity other) {
        return other != null && id.equals(other.id)
                && loanAccount.getLoanId().equals(other.getLoanAccount().getLoanId());
    }

    public void initAndValidateBankAccount(LoanAccountEntity loanAccountEntity) {
        this.loanAccount = loanAccountEntity;
    }
}
