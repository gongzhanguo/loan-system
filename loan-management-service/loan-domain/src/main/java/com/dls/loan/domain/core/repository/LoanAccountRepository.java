package com.dls.loan.domain.core.repository;

import com.dls.loan.dataaccess.loan.mapper.LoanAccountDataAccessMapper;
import com.dls.loan.domain.core.valueobject.LoanId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanAccountRepositoryImpl {

    private LoanAccountJpaRepository loanAccountJpaRepository;
    private LoanAccountDataAccessMapper loanAccountDataAccessMapper;

    public LoanAccountRepositoryImpl(LoanAccountJpaRepository loanAccountJpaRepository,
                                     LoanAccountDataAccessMapper loanAccountDataAccessMapper) {
        this.loanAccountJpaRepository = loanAccountJpaRepository;
        this.loanAccountDataAccessMapper = loanAccountDataAccessMapper;
    }

    public LoanAccount save(LoanAccount loanAccount) {
        loanAccountJpaRepository.save(loanAccountDataAccessMapper.loanAccountToLoanAccountEntity(loanAccount));
        return loanAccount;
    }

    public Optional<LoanAccount> findByAccountId(LoanId loanId) {
        return loanAccountJpaRepository.findById(loanId.getValue())
                .map(loanAccountDataAccessMapper::loanAccountEntityToLoanAccount);
    }
}
