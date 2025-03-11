package com.dls.loan.application;

import com.dls.loan.application.dto.repayment.LoanRepaymentCommand;
import com.dls.loan.application.dto.repayment.LoanRepaymentResponse;

public interface LoanRepaymentAppService {

    LoanRepaymentResponse repaymentLoan(LoanRepaymentCommand loanRepaymentCommand);
}
