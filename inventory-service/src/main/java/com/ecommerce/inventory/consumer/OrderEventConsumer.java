package com.ecommerce.inventory.consumer;

import com.ecommerce.inventory.event.OrderPlacedEvent;
import com.ecommerce.inventory.service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {
		private final InventoryService inventoryService;
		private final ObjectMapper objectMapper;
		@KafkaListener(topics = "order-event",
				       groupId = "inventory-group"
		)
		public void consume(String payload) {
			log.info("Received Order Event: {}", payload);

            try {
				OrderPlacedEvent orderPlacedEvent = objectMapper.readValue(payload, OrderPlacedEvent.class);

				inventoryService.updateStock(orderPlacedEvent);
            } catch (JsonProcessingException e) {
				log.error("Failed to deserialize OrderPlacedEvent", e);
				throw new RuntimeException("Invalid order event", e);
            }
        }
}
