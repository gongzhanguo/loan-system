package com.dls.loan.domain.application.service.dto.disbursement;

import com.dls.loan.domain.core.valueobject.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DisbursementLoanResponse {

    @NotNull
    private final UUID accountId;
    @NotNull
    private final UUID trackingId;
    @NotNull
    private final TransactionStatus transactionStatus;
    @NotNull
    private final String message;
}
