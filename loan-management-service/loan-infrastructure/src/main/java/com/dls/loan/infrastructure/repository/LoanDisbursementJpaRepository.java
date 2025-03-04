package com.dls.loan.infrastructure.repository;

import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.repository.LoanDisbursementRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanDisbursementJpaRepository extends JpaRepository<LoanDisbursementEntity, String>, LoanDisbursementRepository {

    LoanDisbursementEntity save(LoanDisbursementEntity loanDisbursementEntity);

    Optional<LoanDisbursementEntity> findByTrackingId(String loanId);

}
