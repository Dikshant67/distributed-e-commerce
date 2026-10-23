package com.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory",indexes = {
		@Index (name = "index_inventory-sku",columnList = "skuCode")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String skuCode;
	@Column(nullable = false)
	private Integer quantity;
}
