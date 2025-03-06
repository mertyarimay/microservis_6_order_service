package com.mertyarimay.order_service.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;


    private void calculateTotalAmount() {
        if (price != null && quantity > 0) {
            this.totalAmount = price.multiply(BigDecimal.valueOf(quantity)); // fiyat * miktar
        }
    }

    @PrePersist
    public void prePersist() {
        calculateTotalAmount();
    }


    @PreUpdate
    public void preUpdate() {
        calculateTotalAmount();
    }





}
