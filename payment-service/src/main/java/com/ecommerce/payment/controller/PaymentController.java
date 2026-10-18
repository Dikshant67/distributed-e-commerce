
		package com.ecommerce.payment.controller;

import com.ecommerce.payment.DTO.PaymentRequest;
import com.ecommerce.payment.DTO.PaymentStatusUpdateRequest;
import com.ecommerce.payment.DTO.PaymentResponse;
import com.ecommerce.payment.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

		@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(
		name = "Payment Management",
		description = "APIs for creating, retrieving, and updating payments"
)
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	@Operation(
			summary = "Create a payment",
			description = "Creates a payment for an order using the specified payment method."
	)
	@ApiResponse(
			responseCode = "201",
			description = "Payment created successfully",
			content = @Content(
					schema = @Schema(implementation = PaymentResponse.class)
			)
	)
	@ApiResponse(
			responseCode = "400",
			description = "Invalid payment request",
			content = @Content
	)
	@ApiResponse(
			responseCode = "409",
			description = "Payment conflict",
			content = @Content
	)
	public ResponseEntity<PaymentResponse> createPayment(
			@Valid @RequestBody PaymentRequest request) {

		PaymentResponse response = paymentService.createPayment(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/order/{orderId}")
	@Operation(
			summary = "Get payment by order ID",
			description = "Retrieves payment details associated with the specified order."
	)
	@ApiResponse(
			responseCode = "200",
			description = "Payment retrieved successfully",
			content = @Content(
					schema = @Schema(implementation = PaymentResponse.class)
			)
	)
	@ApiResponse(
			responseCode = "404",
			description = "Payment not found",
			content = @Content
	)
	public ResponseEntity<List<PaymentResponse>> getByOrderId(
			@Parameter(
					description = "Unique identifier of the order",
					example = "ORD-1001",
					required = true
			)
			@PathVariable("orderId") String orderId) {

		List<PaymentResponse> response =  paymentService.getPaymentByOrderId(orderId);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{paymentId}/status")
	@Operation(
			summary = "Update payment status",
			description = "Updates the status of an existing payment."
	)
	@ApiResponse(
			responseCode = "200",
			description = "Payment status updated successfully",
			content = @Content(
					schema = @Schema(implementation = PaymentResponse.class)
			)
	)
	@ApiResponse(
			responseCode = "400",
			description = "Invalid payment status",
			content = @Content
	)
	@ApiResponse(
			responseCode = "404",
			description = "Payment not found",
			content = @Content
	)
	public ResponseEntity<PaymentResponse> updatedStatus(
			@Parameter(
					description = "Unique identifier of the payment",
					example = "1",
					required = true
			)
			@PathVariable("paymentId") Long paymentId,

			@Valid @RequestBody PaymentStatusUpdateRequest request) {

		PaymentResponse response =
				paymentService.updatePaymentStatus(paymentId, request);

		return ResponseEntity.ok(response);
	}
}