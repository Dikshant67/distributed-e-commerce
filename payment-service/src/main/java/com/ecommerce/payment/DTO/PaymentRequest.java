package com.ecommerce.payment.DTO;


import com.ecommerce.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating a payment")
public class PaymentRequest {

	@NotBlank
	@Schema(
			description = "Unique identifier of the order",
			example = "ORD-1001",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private String orderId;

	@NotNull
	@Positive
	@Schema(
			description = "Payment amount; must be greater than zero",
			example = "2499.99",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private BigDecimal amount;

	@NotNull
	@Schema(
			description = "Payment method",
			example = "CARD",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private PaymentMethod paymentMethod;
}
