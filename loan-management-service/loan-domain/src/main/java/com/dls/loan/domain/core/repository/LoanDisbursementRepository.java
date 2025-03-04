package com.dls.loan.dataaccess.loan.adapter;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanDisbursementRepositoryImpl {

    public LoanDisbursement save(LoanDisbursement loanDisbursement) {
        return null;
    }

    public Optional<LoanDisbursement> findByLoanId(LoanId loanId) {
        return Optional.empty();
    }
}
