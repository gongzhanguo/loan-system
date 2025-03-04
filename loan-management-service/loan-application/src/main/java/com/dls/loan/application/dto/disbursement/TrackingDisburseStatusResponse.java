package com.dls.loan.application.dto.disbursement;

import com.dls.loan.domain.core.enums.DisbursementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrackingDisburseStatusResponse {

    private final String trackingId;
    private final DisbursementStatus disbursementStatus;
    private final String fileType;
}
