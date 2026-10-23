package com.ecommerce.payment.DTO;


import com.ecommerce.payment.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for updating payment status")
public class PaymentStatusUpdateRequest {

	@NotNull
	@Schema(
			description = "New payment status",
			example = "SUCCESS",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private PaymentStatus paymentStatus;
}
