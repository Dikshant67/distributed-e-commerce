package com.ecommerce.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.order.event.OrderPlacedEvent;
// INFO : No longer needed as outbox publisher does this work
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventProducer {

	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	public void sendOrderEvent(OrderPlacedEvent event) {

		kafkaTemplate.send("order-event", event.getOrderId(), event);

		log.info("Sent Order Event : {}",event);

	}
}
