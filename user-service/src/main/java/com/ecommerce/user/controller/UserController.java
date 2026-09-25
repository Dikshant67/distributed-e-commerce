package com.ecommerce.user.controller;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreatedRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @RequestBody @Valid UserCreatedRequestDto request) {

        log.info("User registration request received");

        UserResponseDto createdUser = userService.register(request);

        log.info("User registration completed successfully: userId={}",
                createdUser.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto request) {

        log.info("User login request received: email={}", request.getEmail());

        LoginResponseDto response = userService.login(request);

        log.info("User login successful: email={}", request.getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long id) {

        log.info("Fetching user: userId={}", id);

        UserResponseDto response = userService.getById(id);

        log.info("User fetched successfully: userId={}", id);

        return ResponseEntity.ok(response);
    }
}