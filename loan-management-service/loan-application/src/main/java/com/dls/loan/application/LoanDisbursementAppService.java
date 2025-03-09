package com.dls.loan.application;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.application.dto.disbursement.TrackingDisburseStatusResponse;

import javax.validation.Valid;

public interface LoanDisbursementAppService {

    DisbursementLoanResponse disburse(@Valid DisbursementLoanCommand disbursementLoanCommand);

    TrackingDisburseStatusResponse trackDisburseStatus(String trackingId);

    void disbursed(String trackingId);

    void loanDisburseFail(String trackingId);
}
