package com.dls.loan.application.ports.input.service;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;

import javax.validation.Valid;

public interface LoanDisbursementAppService {

    DisbursementLoanResponse disburse(@Valid DisbursementLoanCommand disbursementLoanCommand);

}
