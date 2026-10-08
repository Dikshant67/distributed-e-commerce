package com.ecommerce.payment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class InventoryRestClient {

	private final RestClient restClient;

	public void commitInventory(String orderId) {
		restClient.post()
				.uri("/api/inventory/{orderId}/commit", orderId)
				.retrieve()
				.toBodilessEntity();
	}

	public void rollbackInventory(String orderId) {
		restClient.post()
				.uri("/api/inventory/{orderId}/rollback", orderId)
				.retrieve()
				.toBodilessEntity();
	}
}