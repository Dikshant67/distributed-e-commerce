package com.ecommerce.product.consumer;

import com.ecommerce.product.event.ProductCacheEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCacheEventListener {
	private final CacheManager cacheManager;

	@KafkaListener(topics = "product-cache-event",groupId = "product-cache-invalidation")
	public void handleProductEvent(ProductCacheEvent event) {
		switch (event.eventType ()){
			case "PRODUCT_CREATED" -> {
				evictProductListCaches();
			}
			case "PRODUCT_UPDATED" -> {
			evictProductCaches(event.productId ());
			evictProductListCaches();
			}
			case "PRODUCT_DELETED" -> {

			}
			default -> {
				System.out.println ("Unknown event type");

			}


		}
	}

	private void evictProductListCaches() {
		Cache cache = cacheManager.getCache ("productPageCache");
		if (cache != null) {
			cache.clear();
		}
	}
	private void evictProductCaches(Long productId) {
		Cache cache = cacheManager.getCache ("productCache");
		if (cache != null) {
			cache.evict (productId);
		}
	}

}
