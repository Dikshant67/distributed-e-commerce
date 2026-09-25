package com.ecommerce.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String ,Object>> handleAlreadyExistsException(ResourceAlreadyExistsException ex){
        Map<String ,Object> map =Map.of("error","Conflict","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String ,Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        Map<String ,Object> map =Map.of("error","Not found","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String ,Object> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((e)->{errorMap.put(e.getField(),e.getDefaultMessage());});
        Map<String ,Object> body= Map.of("error","validation error","details",errorMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String ,Object>> handleGenericException(Exception ex){
        Map<String ,Object> errorMap = Map.of("error","Internal Server Error","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
    }
}
