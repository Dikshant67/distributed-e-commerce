package com.ecommerce.product.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {
	private static final String TOPIC = "product-cache-event";
	private final KafkaTemplate<String, Object> kafkaTemplate;
	public void publishProductCreated(Long productId) {
		ProductCacheEvent event = new ProductCacheEvent("PRODUCT_CREATED",productId);
		kafkaTemplate.send (TOPIC,productId.toString (),event);


	}
	public void publishProductUpdated(Long productId) {
		ProductCacheEvent event = new ProductCacheEvent("PRODUCT_UPDATED",productId);
		kafkaTemplate.send (TOPIC,productId.toString (),event);
	}
	public void publishProductDeleted(Long productId) {
		ProductCacheEvent event = new ProductCacheEvent("PRODUCT_DELETED",productId);
		kafkaTemplate.send (TOPIC,productId.toString (),event);
	}

}
