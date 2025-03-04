package com.dls.loan.domain.core;


import com.dls.loan.domain.core.enums.DomainEventType;

public interface DomainEventPublisher {
    void publishEvent(String trackingId, DomainEventType domainEventType, Object payload);
}
