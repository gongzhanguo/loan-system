package com.dls.infrastructure.repository;

import com.dls.loan.domain.core.model.LoanDisbursement;
import com.dls.loan.domain.core.repository.LoanDisbursementRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanDisbursementJpaRepository extends CrudRepository<LoanDisbursement, String>, LoanDisbursementRepository {

    LoanDisbursement save(LoanDisbursement loanDisbursement);

    Optional<LoanDisbursement> findByLoanId(String loanId);

}
