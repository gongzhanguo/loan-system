package com.dls.loan.application;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.application.mapper.LoanDisbursementDataMapper;
import com.dls.loan.application.ports.input.service.LoanDisbursementService;
import com.dls.loan.domain.core.event.LoanDisbursedEvent;
import com.dls.loan.domain.core.exception.LoanDomainException;
import com.dls.loan.domain.core.model.LoanDisbursement;
import com.dls.loan.domain.core.repository.LoanDisbursementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    private final LoanDisbursementDataMapper loanDisbursementDataMapper;
    private final LoanDisbursementRepository loanDisbursementRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public LoanDisbursementServiceImpl(LoanDisbursementDataMapper loanDisbursementDataMapper,
                                       LoanDisbursementRepository loanDisbursementRepository,
                                       ApplicationEventPublisher applicationEventPublisher) {
        this.loanDisbursementDataMapper = loanDisbursementDataMapper;
        this.loanDisbursementRepository = loanDisbursementRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public DisbursementLoanResponse disburse(DisbursementLoanCommand disbursementLoanCommand) {
        LoanDisbursement loanDisbursement = loanDisbursementDataMapper.disbursementLoanCommandToDisbursement(disbursementLoanCommand);
        loanDisbursement.validate();
        LoanDisbursedEvent loanDisbursedEvent = saveLoanDisbursement(loanDisbursement);

        DisbursementLoanResponse disbursementLoanResponse = loanDisbursementDataMapper
                .loanDisbursementToDisbursementLoanResponse(loanDisbursement);

        applicationEventPublisher.publishEvent(loanDisbursedEvent);
        // TODO 通知支付网关付款

        return disbursementLoanResponse;
    }

    private LoanDisbursedEvent saveLoanDisbursement(LoanDisbursement loanDisbursement) {
        LoanDisbursement loanDisbursementResult = loanDisbursementRepository.save(loanDisbursement);
        if (loanDisbursementResult == null) {
            log.error("贷款放款信息存储数据库失败！");
            throw new LoanDomainException("贷款放款信息存储数据库失败！");
        }
        log.info("贷款放款信息存储成功，借据号：{}", loanDisbursement.getLoanId().getValue());
        return new LoanDisbursedEvent(loanDisbursement);
    }
}
