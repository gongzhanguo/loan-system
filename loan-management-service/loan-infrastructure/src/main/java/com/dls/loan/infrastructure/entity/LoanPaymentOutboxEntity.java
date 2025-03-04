package com.dls.loan.application.entity;

import com.dls.loan.application.enums.PaymentOutboxStatus;
import com.dls.loan.domain.core.entity.base.BaseDomainEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "loan_payment_outbox")
@Entity
public class LoanPaymentOutboxEntity extends BaseDomainEntity<LoanPaymentOutboxEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String payload;
    @Enumerated(EnumType.STRING)
    private PaymentOutboxStatus paymentOutboxStatus;

    @Override
    protected boolean sameIdentityAs(LoanPaymentOutboxEntity other) {
        return other != null && id.equals(other.id)
                && trackingId.equals(other.getTrackingId());
    }
}
