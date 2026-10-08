package com.ecommerce.payment.DTO;

import com.ecommerce.payment.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
		private Long PaymentId;
		private String orderId;
		private BigDecimal amount;
		private PaymentStatus paymentStatus;
		private String transactionId;
}
