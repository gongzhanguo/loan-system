package com.dls.loan.application.dto.disbursement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 贷款发放相关参数信息类，包含贷款发放和还款计划的核心属性
 */
@Getter
@Builder
@AllArgsConstructor
public class DisbursementLoanCommand {
    /**
     * 贷款发放日期，格式为YYYYMMDD
     */
    @NotBlank(message = "放款日期不能为空")
    @Pattern(regexp = "\\d{4}\\d{2}\\d{2}", message = "日期格式必须为YYYYMMDD")
    private String disbursementDate;

    /**
     * 贷款唯一追踪标识，用于业务流程跟踪
     */
    @NotBlank(message = "追踪ID不能为空")
    @Size(max = 32, message = "追踪ID最长32字符")
    private String trackingId;

    /**
     * 金融产品标识码，关联产品目录
     */
    @NotBlank(message = "产品ID不能为空")
    private String productId;

    /**
     * 贷款发放金额，使用BigDecimal保证金额计算精度
     */
    @NotNull(message = "放款金额不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "金额必须大于0")
    private BigDecimal disbursementAmount;

    /**
     * 客户唯一标识，关联客户主数据
     */
    @NotBlank(message = "客户ID不能为空")
    private String customerId;

    /**
     * 贷款到期日，格式为YYYY-MM-DD
     */
    @NotBlank(message = "到期日不能为空")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "日期格式必须为YYYY-MM-DD")
    private String maturityDate;
    /**
     * 货币类型，采用ISO 4217标准代码（如USD/CNY）
     */
    @NotBlank(message = "货币类型不能为空")
    @Size(min = 3, max = 3, message = "必须符合ISO 4217三位字母标准")
    private String currency;
    /**
     * 还款方式（等额本息/等额本金/利随本清等）
     */
    @NotBlank(message = "还款方式不能为空")
    private String repaymentMethod;
    /**
     * 基础年利率，使用private BigDecimal表示精确小数
     */
    @NotNull(message = "利率不能为空")
    @DecimalMin(value = "0.0", message = "利率不能为负数")
    private BigDecimal interestRate;
    /**
     * 逾期罚息利率，通常高于基础利率
     */
    @NotNull(message = "逾期利率不能为空")
    @DecimalMin(value = "0.0", message = "逾期利率不能为负数")
    private BigDecimal overdueInterestRate;
    /**
     * 还款周期（按月/季/年等）
     */
    @NotBlank(message = "还款周期不能为空")
    private String repaymentCycle;
    /**
     * 总还款期数（单位与还款周期匹配）
     */
    @NotNull(message = "总期数不能为空")
    @Min(value = 1, message = "期数至少1期")
    private Integer totalTerms;
    /**
     * 宽限期天数（允许延迟还款的天数）
     */
    @NotNull(message = "宽限期不能为空")
    @Min(value = 0, message = "宽限期不能为负数")
    private Integer gracePeriod;
    /**
     * 放款账户信息，包含银行账户详细信息
     */
    @NotNull(message = "放款账户不能为空")
    @Valid  // 级联校验BankAccount内部字段
    private BankAccountDTO paymentBankAccount;
    /**
     * 自动扣款账户信息，包含银行账户验证状态
     */
    @NotNull(message = "扣款账户不能为空")
    @Valid
    private BankAccountDTO autoDeductionBankAccount;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class BankAccountDTO {

        @NotBlank(message = "客户姓名不能为空")
        @Size(max = 50, message = "客户姓名最多50个字符")
        private String customerName;

        @NotBlank(message = "证件号码不能为空")
        @Pattern(regexp = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)",
                message = "身份证号格式不正确")
        private String identityNo;

        @NotBlank(message = "银行账号不能为空")
        @Size(min = 16, max = 19, message = "账号长度16-19位")
        @Pattern(regexp = "^\\d+$", message = "账号必须为纯数字")
        private String accountNo;

        @NotBlank(message = "开户行名称不能为空")
        @Size(max = 100, message = "银行名称最多100字符")
        private String bankName;

        @NotBlank(message = "联行号不能为空")
        @Pattern(regexp = "^\\d{12}$", message = "联行号必须为12位数字")
        private String bankNo;

    }
}
