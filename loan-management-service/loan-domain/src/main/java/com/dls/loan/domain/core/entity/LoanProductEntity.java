package com.dls.loan.domain.core.entity;

import com.dls.loan.domain.core.enums.PeriodType;
import com.dls.loan.domain.core.enums.RepaymentOrder;

import java.math.BigDecimal;

public class LoanProductEntity {

    String productId;
    RepaymentOrder repaymentOrder;
    BigDecimal maxLoanAmount;
    Integer maxLoanPeriod;
    PeriodType maxLoanPeriodType;

}
