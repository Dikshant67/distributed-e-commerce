package com.ecommerce.payment.entity;

import com.ecommerce.payment.enums.PaymentMethod;
import com.ecommerce.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
		@Index(columnList = "orderId"),
		@Index(columnList = "transactionId")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String orderId;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod paymentMethod;

	@Column(unique = true, nullable = false)
	private String transactionId;

	private String gatewayPaymentId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
		
	@PrePersist
	void onCreate() {
			this.createdAt = LocalDateTime.now();
			this.updatedAt = LocalDateTime.now();
		}
		@PreUpdate
		void onUpdate() {
			this.updatedAt = LocalDateTime.now();
		}
}
