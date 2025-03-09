package com.dls.loan.domain.core.service;

import com.dls.loan.domain.core.entity.BankAccountEntity;
import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.event.LoanDisburseEvent;

import java.util.List;

public interface LoanDisbursementService {

    LoanDisburseEvent disburse(LoanDisbursementEntity loanDisbursementEntity, List<BankAccountEntity> bankAccountEntities);

    void disbursed(LoanDisbursementEntity loanDisbursementEntity);

    void reversal(LoanDisbursementEntity loanDisbursementEntity);
}
