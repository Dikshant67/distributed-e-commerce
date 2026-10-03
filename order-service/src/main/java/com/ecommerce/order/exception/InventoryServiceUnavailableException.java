package com.ecommerce.order.exception;

import org.springframework.web.client.ResourceAccessException;

public class InventoryServiceUnavailableException extends RuntimeException {
    public InventoryServiceUnavailableException(String inventoryServiceIsUnavailable, ResourceAccessException e) {
        super(inventoryServiceIsUnavailable);
    }
}
