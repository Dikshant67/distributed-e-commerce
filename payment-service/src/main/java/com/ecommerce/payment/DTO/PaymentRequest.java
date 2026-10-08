package com.ecommerce.payment.DTO;

import com.ecommerce.payment.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
		  @NotNull String orderId;
		@Positive private BigDecimal amount;
		@NotNull private PaymentMethod paymentMethod;
}
