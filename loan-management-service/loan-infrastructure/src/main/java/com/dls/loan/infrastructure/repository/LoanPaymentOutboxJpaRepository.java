package com.dls.loan.infrastructure.repository;

import com.dls.loan.application.enums.PaymentOutboxStatus;
import com.dls.loan.infrastructure.entity.LoanPaymentOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentOutboxJpaRepository extends JpaRepository<LoanPaymentOutboxEntity, String> {

    LoanPaymentOutboxEntity save(LoanPaymentOutboxEntity loanPaymentOutboxEntity);

    List<LoanPaymentOutboxEntity> findByOutboxStatus(PaymentOutboxStatus outboxStatus);

    @Modifying
    @Query(value = "update loan_payment_outbox set payment_outbox_status = :outboxStatus where tracking_id = :trackingId",
            nativeQuery = true)
    void updateOutboxStatusByTrackingId(@Param("trackingId") String trackingId,
                                        @Param("outboxStatus") PaymentOutboxStatus outboxStatus);
}
