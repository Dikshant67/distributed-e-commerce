package com.ecommerce.payment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OrderRestClient {

    private final RestClient restClient;

    public void confirmOrder(String orderId) {
        restClient.post()
                .uri("/api/order/{orderId}/confirm", orderId)
                .retrieve()
                .toBodilessEntity();
    }

    public void failOrder(String orderId) {
        restClient.post()
                .uri("/api/order/{orderId}/fail", orderId)
                .retrieve()
                .toBodilessEntity();
    }
}