package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.dto.InventoryRequest;
import com.ecommerce.inventory.dto.InventoryResponse;
import com.ecommerce.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Tag(
		name = "Inventory",
		description = "APIs for managing product inventory and checking stock availability"
)
public class InventoryController {

	private final InventoryService inventoryService;

	@Operation(
			summary = "Check product inventory",
			description = "Checks whether a product is in stock for the given SKU code "
					+ "and returns the available quantity."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Inventory information retrieved successfully",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = InventoryResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "Inventory not found for the given SKU code",
					content = @Content
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal server error",
					content = @Content
			)
	})
	@GetMapping("/{skuCode}")
	public ResponseEntity<InventoryResponse> isInStock(
			@Parameter(
					description = "Unique SKU code of the product",
					example = "IPHONE-15-128GB",
					required = true
			)
			@PathVariable String skuCode) {

		return ResponseEntity.ok(
				inventoryService.checkInventory(skuCode)
		);
	}

	@Operation(
			summary = "Add inventory",
			description = "Adds or updates inventory for a product using its SKU code and quantity."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Inventory added successfully"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid inventory request",
					content = @Content
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal server error",
					content = @Content
			)
	})
	@PostMapping
	public ResponseEntity<String> addInventory(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Inventory details",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = InventoryRequest.class)
					)
			)
			@Valid @RequestBody InventoryRequest request) {

		inventoryService.addInventory(request);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Inventory added successfully");
	}
}