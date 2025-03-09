package com.dls.loan.domain.core.repository;

import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.enums.DisbursementStatus;

import java.util.Optional;

public interface LoanDisbursementRepository {

    LoanDisbursementEntity save(LoanDisbursementEntity loanDisbursementEntity);

    Optional<LoanDisbursementEntity> findByTrackingId(String trackingId);

}
