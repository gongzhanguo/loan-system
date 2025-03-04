package com.dls.loan.domain.core.repository;

import com.dls.loan.domain.core.entity.LoanAccountEntity;

import java.util.Optional;

public interface LoanAccountRepository {

    LoanAccountEntity save(LoanAccountEntity loanAccountEntity);

    Optional<LoanAccountEntity> findByLoanId(String loanId);
}
