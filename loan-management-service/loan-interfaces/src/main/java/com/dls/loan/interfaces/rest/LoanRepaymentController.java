package com.dls.loan.interfaces.rest;

import com.dls.loan.application.LoanRepaymentAppService;
import com.dls.loan.application.dto.repayment.LoanRepaymentCommand;
import com.dls.loan.application.dto.repayment.LoanRepaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoanRepaymentController {

    private final LoanRepaymentAppService loanRepaymentAppService;

    public LoanRepaymentController(LoanRepaymentAppService loanRepaymentAppService) {
        this.loanRepaymentAppService = loanRepaymentAppService;
    }

    public ResponseEntity<LoanRepaymentResponse> loanRepayment(LoanRepaymentCommand loanRepaymentCommand) {
        return ResponseEntity.ok(loanRepaymentAppService.repaymentLoan(loanRepaymentCommand));
    }
}
