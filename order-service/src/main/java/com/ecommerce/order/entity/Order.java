package com.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "orders", indexes = {
		@Index(name = "idx_order_number", columnList = "order_number"),
		@Index(name = "idx_order_status", columnList = "order_status"),
		@Index (name = "idx_order_status_sku" , columnList = "order_status,sku_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "sku_code")
	private String skuCode;
	private Integer quantity;
	private BigDecimal price;

	@Column(name = "order_status")
	private String orderStatus;
}