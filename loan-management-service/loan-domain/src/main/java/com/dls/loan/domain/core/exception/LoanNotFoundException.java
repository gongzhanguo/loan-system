package com.dls.loan.domain.core.exception;


import com.dls.common.domain.exception.DomainException;

public class LoanNotFoundException extends DomainException {

    public LoanNotFoundException(String message) {
        super(message);
    }

    public LoanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
