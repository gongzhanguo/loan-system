package com.dls.loan.application.dto.repayment;

import com.dls.loan.domain.core.enums.RepaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class RepaymentLoanResponse {

    @NotNull
    private final String loanId;
    @NotNull
    private final String trackingId;
    @NotNull
    private final RepaymentStatus repaymentStatus;
    @NotNull
    private final String message;
}
