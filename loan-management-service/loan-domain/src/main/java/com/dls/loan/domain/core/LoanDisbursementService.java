package com.dls.loan.domain.core;

import com.dls.loan.domain.core.entity.BankAccountEntity;
import com.dls.loan.domain.core.entity.LoanAccountEntity;
import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.event.LoanDisbursedEvent;

import java.util.List;

public interface LoanDisbursementService {

    LoanDisbursedEvent disburse(LoanDisbursementEntity loanDisbursementEntity, List<BankAccountEntity> bankAccountEntities);

    void disbursed(LoanDisbursementEntity loanDisbursementEntity);

    void reversal(LoanDisbursementEntity loanDisbursementEntity);
}
