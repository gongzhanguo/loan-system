package com.dls.loan.infrastructure;

import com.dls.loan.application.enums.PaymentOutboxStatus;
import com.dls.loan.domain.core.event.DomainEvent;
import com.dls.loan.domain.core.event.DomainEventPublisher;
import com.dls.loan.domain.core.enums.DomainEventType;
import com.dls.loan.domain.core.exception.LoanDomainException;
import com.dls.loan.infrastructure.entity.LoanPaymentOutboxEntity;
import com.dls.loan.infrastructure.repository.LoanPaymentOutboxJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {
    private final LoanPaymentOutboxJpaRepository loanPaymentOutboxJpaRepository;
    private final ObjectMapper objectMapper;

    public DomainEventPublisherImpl(LoanPaymentOutboxJpaRepository loanPaymentOutboxJpaRepository,
                                    ObjectMapper objectMapper) {
        this.loanPaymentOutboxJpaRepository = loanPaymentOutboxJpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishEvent(String trackingId, DomainEventType domainEventType, DomainEvent payload) {
        LoanPaymentOutboxEntity loanPaymentOutboxEntity = LoanPaymentOutboxEntity.builder()
                .trackingId(trackingId)
                .domainEventType(domainEventType)
                .payload(createPayload(trackingId, payload))
                .outboxStatus(PaymentOutboxStatus.PENDING)
                .build();
        loanPaymentOutboxJpaRepository.save(loanPaymentOutboxEntity);
    }

    private String createPayload(String trackingId, DomainEvent payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("领域事件信息转化字符串失败，交易跟踪号：{}", trackingId, e);
            throw new LoanDomainException("领域事件信息转化字符串失败，交易跟踪号：" + trackingId, e);
        }
    }
}
