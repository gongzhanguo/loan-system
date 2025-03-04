package com.dls.loan.application.mapper;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.domain.core.entity.BankAccountEntity;
import com.dls.loan.domain.core.enums.BankAccountType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LoanBankAccountDataMapper {

    public List<BankAccountEntity> bankAccountDTOToBankAccountEntities(
            DisbursementLoanCommand.BankAccountDTO paymentBankAccount,
            DisbursementLoanCommand.BankAccountDTO autoDeductionBankAccount) {
        BankAccountEntity paymentBankAccountEntity = BankAccountEntity.builder()
                .customerName(paymentBankAccount.getCustomerName())
                .identityNo(paymentBankAccount.getIdentityNo())
                .accountNo(paymentBankAccount.getAccountNo())
                .bankName(paymentBankAccount.getBankName())
                .bankNo(paymentBankAccount.getBankNo())
                .bankAccountType(BankAccountType.PAYMENT)
                .build();
        BankAccountEntity autoDeductionBankAccountEntity = BankAccountEntity.builder()
                .customerName(autoDeductionBankAccount.getCustomerName())
                .identityNo(autoDeductionBankAccount.getIdentityNo())
                .accountNo(autoDeductionBankAccount.getAccountNo())
                .bankName(autoDeductionBankAccount.getBankName())
                .bankNo(autoDeductionBankAccount.getBankNo())
                .bankAccountType(BankAccountType.AUTO_DEDUCTION)
                .build();
        return Arrays.asList(paymentBankAccountEntity, autoDeductionBankAccountEntity);
    }
}
