package com.ecommerce.inventory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> handleInventoryNotFoundException(InventoryNotFoundException e) {

        Map<String,Object> map = new HashMap<>();

        map.put("message",e.getMessage());

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
