package com.dls.loan.domain.core.event;

import com.dls.loan.domain.core.entity.LoanDisbursement;

import java.time.LocalDateTime;

public class LoanDisbursedEvent extends LoanDisbursementEvent {

    public LoanDisbursedEvent(LoanDisbursement loanDisbursement, LocalDateTime createTimestamp) {
        super(loanDisbursement, createTimestamp);
    }
}
