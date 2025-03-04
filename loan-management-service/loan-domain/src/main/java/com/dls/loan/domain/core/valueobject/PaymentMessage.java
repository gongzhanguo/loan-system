package com.dls.loan.domain.core.valueobject;

import com.dls.loan.domain.core.entity.BankAccountEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMessage {

    private String loanId;
    private String trackingId;
    private BankAccountEntity bankAccount;
}
