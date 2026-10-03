package com.ecommerce.order.client;

import com.ecommerce.order.dto.InventoryResponse;
import com.ecommerce.order.exception.InventoryProductNotFoundException;
import com.ecommerce.order.exception.InventoryServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class InventoryRestClient {

    private final RestClient restClient;

    public InventoryResponse isInStock(String skuCode) throws InventoryServiceUnavailableException,InventoryProductNotFoundException {

        try {
            return restClient.get()
                    .uri("/api/inventory/{skuCode}", skuCode)
                    .retrieve()

                    .onStatus(
                            status -> status.value() == 404,
                            (request, response) -> {
                                throw new InventoryProductNotFoundException(
                                        "Invalid SKU code: " + skuCode
                                );
                            }
                    )

                    .body(InventoryResponse.class);

        } catch (InventoryProductNotFoundException e) {
            throw e;

        } catch (ResourceAccessException e) {
            throw new InventoryServiceUnavailableException(
                    "Inventory service is unavailable",
                    e
            );
        }
    }
}