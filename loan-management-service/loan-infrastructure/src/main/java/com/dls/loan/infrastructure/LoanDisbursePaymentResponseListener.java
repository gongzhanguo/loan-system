package com.dls.loan.infrastructure;

import com.dls.loan.application.LoanDisbursementAppService;
import com.dls.loan.domain.core.event.LoanDisburseEvent;
import com.dls.loan.domain.core.valueobject.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class LoanDisbursePaymentResponseListener {
    private final ObjectMapper objectMapper;
    private final LoanDisbursementAppService loanDisbursementAppService;

    public LoanDisbursePaymentResponseListener(ObjectMapper objectMapper, LoanDisbursementAppService loanDisbursementAppService) {
        this.objectMapper = objectMapper;
        this.loanDisbursementAppService = loanDisbursementAppService;
    }

    @KafkaListener(id = "${kafka-consumer-config.customer-group-id}",
            topics = "${loan-service.loan-disbursed-topic-name}")
    public void receive(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            Optional<String> message = Optional.ofNullable(record.value());
            if (!message.isPresent()) {
                return;
            }
            log.info("Received message: {}", message.get());
            LoanDisburseEvent loanDisburseEvent = objectMapper.convertValue(message, LoanDisburseEvent.class);
            if (loanDisburseEvent.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
                loanDisbursementAppService.disbursed(loanDisburseEvent.getTrackingId());
            } else if (loanDisburseEvent.getPaymentStatus().equals(PaymentStatus.FAIL)) {
                loanDisbursementAppService.loanDisburseFail(loanDisburseEvent.getTrackingId());
            }
        } catch (Exception e) {
            log.error("放款消息处理失败，放款信息：{}，异常信息：{}", record.value(), e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }
}
