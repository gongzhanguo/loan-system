package com.dls.infrastructure.repository;

import com.dls.loan.domain.core.model.LoanAccount;
import com.dls.loan.domain.core.repository.LoanAccountRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanAccountJpaRepository extends CrudRepository<LoanAccount, String>, LoanAccountRepository {

    LoanAccount save(LoanAccount loanAccount);

    Optional<LoanAccount> findByLoanId(String loanId);

}
