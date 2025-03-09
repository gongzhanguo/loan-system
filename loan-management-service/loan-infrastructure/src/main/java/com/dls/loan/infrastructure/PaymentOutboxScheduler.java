package com.dls.loan.infrastructure;

import com.dls.loan.application.config.LoanServiceConfig;
import com.dls.loan.application.enums.PaymentOutboxStatus;
import com.dls.loan.domain.core.enums.DomainEventType;
import com.dls.loan.infrastructure.entity.LoanPaymentOutboxEntity;
import com.dls.loan.infrastructure.repository.LoanPaymentOutboxJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class PaymentOutboxScheduler {

    private final LoanPaymentOutboxJpaRepository loanPaymentOutboxJpaRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LoanServiceConfig loanServiceConfig;

    public PaymentOutboxScheduler(LoanPaymentOutboxJpaRepository loanPaymentOutboxJpaRepository, KafkaTemplate<String, String> kafkaTemplate, LoanServiceConfig loanServiceConfig) {
        this.loanPaymentOutboxJpaRepository = loanPaymentOutboxJpaRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.loanServiceConfig = loanServiceConfig;
    }

    /**
     * 定时处理待处理支付发件箱消息
     *
     * @Scheduled 定时任务注解，配置为每1000毫秒执行一次（即每秒执行一次）
     * <p>
     * 方法逻辑：
     * 1. 从持久层获取所有状态为PENDING的发件箱消息
     * 2. 遍历每个待处理消息进行后续处理（当前处理逻辑待实现）
     *
     * // TODO: 防止消息重复发送，定时任务触发应使用分布式调度工具，如XXLJOB、Quartz、PowerJob等
     */
    @Scheduled(fixedDelayString = "${loan-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${loan-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        List<LoanPaymentOutboxEntity> outboxMessages = loanPaymentOutboxJpaRepository
                .findByOutboxStatus(PaymentOutboxStatus.PENDING);

        for (LoanPaymentOutboxEntity outboxMessage : outboxMessages) {
            kafkaTemplate.send(getTopicName(outboxMessage.getDomainEventType()), outboxMessage.getTrackingId(),
                    outboxMessage.getPayload()).addCallback(
                    success -> {
                        loanPaymentOutboxJpaRepository.updateOutboxStatusByTrackingId(outboxMessage.getTrackingId(), PaymentOutboxStatus.SUCCESS);
                        log.info("推送Kafka成功，流水号：{}", outboxMessage.getTrackingId());
                    },
                    failure -> {
                        loanPaymentOutboxJpaRepository.updateOutboxStatusByTrackingId(outboxMessage.getTrackingId(), PaymentOutboxStatus.FAILED);
                        log.info("推送Kafka失败，流水号：{}", outboxMessage.getTrackingId());
                    });
        }
    }

    private String getTopicName(DomainEventType domainEventType) {
        switch (domainEventType) {
            case LOAN_DISBURSE:
                return loanServiceConfig.getLoanDisbursementTopicName();
            case LOAN_DISBURSE_SUCCESS:
                return loanServiceConfig.getLoanDisbursedTopicName();
            default:
                return "";
        }
    }
}
