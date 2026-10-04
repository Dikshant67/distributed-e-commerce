package com.ecommerce.order.service;

import com.ecommerce.order.constant.OutboxStatus;
import com.ecommerce.order.entity.OutboxEvent;
import com.ecommerce.order.event.OrderPlacedEvent;
import com.ecommerce.order.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public void saveOrderPlacedEvent(OrderPlacedEvent event) {

        try {

            String payload =
                    objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .id(UUID.fromString(event.getEventId()))
                    .aggregateType("ORDER")
                    .aggregateId(event.getOrderId())
                    .eventType("ORDER_PLACED")
                    .payload(payload)
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .retryCount(0)
                    .build();

            outboxEventRepository.save(outboxEvent);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Failed to serialize OrderPlacedEvent", e
            );
        }
    }
}