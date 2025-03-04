package com.dls.loan.domain.core.exception;


public class LoanDomainException extends DomainException {

    public LoanDomainException(String message) {
        super(message);
    }

    public LoanDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
