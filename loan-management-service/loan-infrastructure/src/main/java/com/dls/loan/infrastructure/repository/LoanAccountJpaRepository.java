package com.dls.loan.infrastructure.repository;

import com.dls.loan.domain.core.entity.LoanAccountEntity;
import com.dls.loan.domain.core.repository.LoanAccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanAccountJpaRepository extends JpaRepository<LoanAccountEntity, String>, LoanAccountRepository {

    LoanAccountEntity save(LoanAccountEntity loanAccountEntity);

    Optional<LoanAccountEntity> findByLoanId(String loanId);

}
