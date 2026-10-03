package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(
		name = "Order",
		description = "APIs for placing and managing customer orders"
)
public class OrderController {

	private final OrderService orderService;

	@Operation(
			summary = "Place a new order",
			description = "Creates a new order for the specified SKU, quantity, and price."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Order placed successfully",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = OrderResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid order request",
					content = @Content
			),
			@ApiResponse(
					responseCode = "409",
					description = "Product is out of stock or order cannot be processed",
					content = @Content
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal server error",
					content = @Content
			)
	})
	@PostMapping
	public ResponseEntity<OrderResponse> placeOrder(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Details required to place an order",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = OrderRequest.class)
					)
			)
			@Valid @RequestBody OrderRequest request) {

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(orderService.PlaceOrder(request));
	}
}