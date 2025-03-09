package com.dls.loan.application;

import com.dls.loan.application.dto.disbursement.DisbursementLoanCommand;
import com.dls.loan.application.dto.disbursement.DisbursementLoanResponse;
import com.dls.loan.application.dto.disbursement.TrackingDisburseStatusResponse;
import com.dls.loan.application.mapper.LoanBankAccountDataMapper;
import com.dls.loan.application.mapper.LoanDisbursementDataMapper;
import com.dls.loan.domain.core.enums.DisburseFailType;
import com.dls.loan.domain.core.service.LoanDisbursementService;
import com.dls.loan.domain.core.entity.BankAccountEntity;
import com.dls.loan.domain.core.entity.LoanDisbursementEntity;
import com.dls.loan.domain.core.enums.DomainEventType;
import com.dls.loan.domain.core.event.DomainEventPublisher;
import com.dls.loan.domain.core.event.LoanDisburseEvent;
import com.dls.loan.domain.core.exception.LoanDomainException;
import com.dls.loan.domain.core.repository.LoanDisbursementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoanDisbursementAppServiceImpl implements LoanDisbursementAppService {

    private final LoanDisbursementService loanDisbursementService;
    private final LoanDisbursementDataMapper loanDisbursementDataMapper;
    private final LoanBankAccountDataMapper loanBankAccountDataMapper;
    private final LoanDisbursementRepository loanDisbursementRepository;
    private final DomainEventPublisher domainEventPublisher;

    public LoanDisbursementAppServiceImpl(LoanDisbursementService loanDisbursementService,
                                          LoanDisbursementDataMapper loanDisbursementDataMapper,
                                          LoanBankAccountDataMapper loanBankAccountDataMapper,
                                          LoanDisbursementRepository loanDisbursementRepository,
                                          DomainEventPublisher domainEventPublisher) {
        this.loanDisbursementService = loanDisbursementService;
        this.loanDisbursementDataMapper = loanDisbursementDataMapper;
        this.loanBankAccountDataMapper = loanBankAccountDataMapper;
        this.loanDisbursementRepository = loanDisbursementRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    @Transactional
    public DisbursementLoanResponse disburse(DisbursementLoanCommand disbursementLoanCommand) {
        LoanDisbursementEntity loanDisbursementEntity = loanDisbursementDataMapper
                .disbursementLoanCommandToDisbursement(disbursementLoanCommand);

        List<BankAccountEntity> bankAccountEntities = loanBankAccountDataMapper.bankAccountDTOToBankAccountEntities(
                disbursementLoanCommand.getPaymentBankAccount(), disbursementLoanCommand.getAutoDeductionBankAccount());

        LoanDisburseEvent loanDisburseEvent = loanDisbursementService
                .disburse(loanDisbursementEntity, bankAccountEntities);

        persistence(loanDisbursementEntity);

        domainEventPublisher.publishEvent(loanDisburseEvent.getTrackingId(),
                DomainEventType.LOAN_DISBURSE, loanDisburseEvent);

        return loanDisbursementDataMapper.loanDisbursementToDisbursementLoanResponse(loanDisbursementEntity);
    }

    @Override
    public TrackingDisburseStatusResponse trackDisburseStatus(String trackingId) {
        Optional<LoanDisbursementEntity> loanDisbursementEntity = loanDisbursementRepository.findByTrackingId(trackingId);
        if (!loanDisbursementEntity.isPresent()) {
            throw new LoanDomainException("贷款放款信息不存在，交易流水号：" + trackingId);
        }
        return TrackingDisburseStatusResponse.builder()
                .trackingId(trackingId)
                .disbursementStatus(loanDisbursementEntity.get().getDisbursementStatus())
                .fileType(loanDisbursementEntity.get().getDisburseFailType().toString())
                .build();
    }

    @Override
    public void disbursed(String trackingId) {
        Optional<LoanDisbursementEntity> loanDisbursement = loanDisbursementRepository.findByTrackingId(trackingId);
        if (!loanDisbursement.isPresent()) {
            log.error("流水号对应放款信息不存在，流水号：{}", trackingId);
            throw new LoanDomainException("流水号对应放款信息不存在，流水号：" + trackingId);
        }
        loanDisbursement.get().disbursed();

        loanDisbursementRepository.save(loanDisbursement.get());
    }

    @Override
    public void loanDisburseFail(String trackingId) {
        Optional<LoanDisbursementEntity> loanDisbursement = loanDisbursementRepository.findByTrackingId(trackingId);
        if (!loanDisbursement.isPresent()) {
            log.error("流水号对应放款信息不存在，流水号：{}", trackingId);
            throw new LoanDomainException("流水号对应放款信息不存在，流水号：" + trackingId);
        }
        loanDisbursement.get().cancel(DisburseFailType.PAYMENT_FAIL);

        loanDisbursementRepository.save(loanDisbursement.get());
    }

    private void persistence(LoanDisbursementEntity loanDisbursementEntity) {
        LoanDisbursementEntity loanDisbursementEntityResult = loanDisbursementRepository.save(loanDisbursementEntity);
        if (loanDisbursementEntityResult == null) {
            log.error("贷款放款信息存储数据库失败！");
            throw new LoanDomainException("贷款放款信息存储数据库失败！");
        }
        log.info("贷款放款信息存储成功，借据号：{}", loanDisbursementEntity.getLoanAccount().getLoanId());
    }
}
