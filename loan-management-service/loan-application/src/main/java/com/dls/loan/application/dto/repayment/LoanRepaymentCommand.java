package com.dls.loan.application.dto.repayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class LoanRepaymentCommand {

    @NotBlank(message = "追踪ID不能为空")
    private String trackingId;

    @NotBlank(message = "贷款ID不能为空")
    private String loanId;

    @NotNull(message = "期号不能为空")
    @Positive(message = "期号必须为正整数")
    private Integer termNo;

    @NotNull(message = "本金金额不能为空")
    @DecimalMin(value = "0.00", inclusive = false, message = "本金金额必须大于0")
    @Digits(integer = 19, fraction = 2, message = "本金金额整数部分最多19位，小数最多2位")
    private BigDecimal principleAmount;

    @NotNull(message = "利息金额不能为空")
    @DecimalMin(value = "0.00", message = "利息金额必须大于0")
    @Digits(integer = 19, fraction = 2, message = "利息金额整数部分最多19位，小数最多2位")
    private BigDecimal interestAmount;

    @NotNull(message = "罚息金额不能为空")
    @DecimalMin(value = "0.00", message = "罚息金额必须大于0")
    @Digits(integer = 19, fraction = 2, message = "罚息金额整数部分最多19位，小数最多2位")
    private BigDecimal penaltyAmount;

    @NotNull(message = "总金额不能为空")
    @DecimalMin(value = "0.00", inclusive = false, message = "总金额必须大于0")
    @Digits(integer = 19, fraction = 2, message = "总金额整数部分最多19位，小数最多2位")
    private BigDecimal totalAmount;

}
