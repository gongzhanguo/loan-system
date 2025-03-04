package com.dls.loan.application.dto.disbursement;

import com.dls.loan.domain.core.enums.DisbursementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DisbursementLoanResponse {

    @NotNull
    private final String loanId;
    @NotNull
    private final String trackingId;
    @NotNull
    private final DisbursementStatus disbursementStatus;
    @NotNull
    private final String message;

}
