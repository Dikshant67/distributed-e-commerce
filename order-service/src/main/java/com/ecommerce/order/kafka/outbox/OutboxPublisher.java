package com.ecommerce.order.kafka.outbox;

import com.ecommerce.order.constant.OutboxStatus;
import com.ecommerce.order.entity.OutboxEvent;
import com.ecommerce.order.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void publishEvents() {

        List<OutboxEvent> events =
                outboxEventRepository
                        .findTop100ByStatusOrderByCreatedAtAsc(
                                OutboxStatus.PENDING
                        );

        for (OutboxEvent event : events) {

            try {

                kafkaTemplate
                        .send(
                                "order-event",
                                event.getAggregateId(),
                                event.getPayload()
                        )
                        .whenComplete((result, exception) -> {

                            if (exception == null) {

                                markAsPublished(event.getId());

                                log.info(
                                        "Outbox event published: {}",
                                        event.getId()
                                );

                            } else {

                                log.error(
                                        "Failed to publish outbox event: {}",
                                        event.getId(),
                                        exception
                                );
                            }
                        });

            } catch (Exception e) {

                log.error(
                        "Error publishing outbox event {}",
                        event.getId(),
                        e
                );
            }
        }
    }

    @Transactional
    public void markAsPublished(UUID eventId) {

        OutboxEvent event =
                outboxEventRepository
                        .findById(eventId)
                        .orElseThrow();

        event.setStatus(OutboxStatus.PUBLISHED);
        event.setPublishedAt(LocalDateTime.now());

        outboxEventRepository.save(event);
    }
}