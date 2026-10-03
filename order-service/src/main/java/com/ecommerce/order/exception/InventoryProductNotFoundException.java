package com.ecommerce.order.exception;

public class InventoryProductNotFoundException extends RuntimeException {
    public InventoryProductNotFoundException(String s) {
        super(s);
    }
}
