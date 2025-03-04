package com.dls.loan.interfaces.rest;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.application.LoanDisbursementAppService;
import com.dls.loan.application.dto.disbursement.TrackingDisburseStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class LoanDisbursementController {

    private final LoanDisbursementAppService loanDisbursementAppService;

    public LoanDisbursementController(LoanDisbursementAppService loanDisbursementAppService) {
        this.loanDisbursementAppService = loanDisbursementAppService;
    }

    @PostMapping("/disbursement")
    public ResponseEntity<DisbursementLoanResponse> disbursement(@RequestBody @Valid DisbursementLoanCommand command) {
        return ResponseEntity.ok(loanDisbursementAppService.disburse(command));
    }

    @GetMapping("/trackDisburseStatus/{trackingId}")
    public ResponseEntity<TrackingDisburseStatusResponse> trackDisburseStatus(@PathVariable String trackingId) {
        return ResponseEntity.ok(loanDisbursementAppService.trackDisburseStatus(trackingId));
    }

}
