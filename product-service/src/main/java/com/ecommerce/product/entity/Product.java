package com.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String description;
    @Column(nullable = false)
    private double price;
    private double discountPrice;
    private int quantity;
    private String brand;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "category_id")
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void OnCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
    @PreUpdate
    public void OnUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
