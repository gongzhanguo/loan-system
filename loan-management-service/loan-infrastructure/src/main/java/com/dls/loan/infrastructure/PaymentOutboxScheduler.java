package com.dls.loan.infrastructure;

import com.dls.loan.application.enums.PaymentOutboxStatus;
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

    public PaymentOutboxScheduler(LoanPaymentOutboxJpaRepository loanPaymentOutboxJpaRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.loanPaymentOutboxJpaRepository = loanPaymentOutboxJpaRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 定时处理待处理支付发件箱消息
     *
     * @Scheduled 定时任务注解，配置为每1000毫秒执行一次（即每秒执行一次）
     * <p>
     * 方法逻辑：
     * 1. 从持久层获取所有状态为PENDING的发件箱消息
     * 2. 遍历每个待处理消息进行后续处理（当前处理逻辑待实现）
     */
    @Scheduled(fixedRate = 1000)
    public void processOutboxMessage() {
        List<LoanPaymentOutboxEntity> outboxMessages = loanPaymentOutboxJpaRepository
                .findByOutboxStatus(PaymentOutboxStatus.PENDING);

        for (LoanPaymentOutboxEntity outboxMessage : outboxMessages) {
            kafkaTemplate.send(outboxMessage.getDomainEventType().toString(), outboxMessage.getPayload()).addCallback(
                    success -> {
                        loanPaymentOutboxJpaRepository.updateOutboxStatusByTrackingId(outboxMessage.getTrackingId(), PaymentOutboxStatus.SUCCESS);
                    },
                    failure -> {
                        loanPaymentOutboxJpaRepository.updateOutboxStatusByTrackingId(outboxMessage.getTrackingId(), PaymentOutboxStatus.FAILED);
                    });
        }
    }
}
