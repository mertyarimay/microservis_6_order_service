package com.mertyarimay.order_service.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class OrderEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     private int customerId;
     private BigDecimal totalAmount = BigDecimal.ZERO;
     private LocalDateTime orderDate;
     private LocalDateTime deliveryDate;
     private String status="Pending";
     @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<OrderItemEntity> orderItemEntities;
     @ManyToOne
     @JoinColumn(name = "adress_id")
     private AdressEntity adressEntity;


    public void recalculateTotalAmount() {
          // Eğer orderItems boş ise sıfır döndür
          if (orderItemEntities == null || orderItemEntities.isEmpty()) {
               this.totalAmount = BigDecimal.ZERO;
          } else {
               // orderItems listesinde bulunan tüm orderItem'ların totalAmount'larını topluyoruz
               this.totalAmount = orderItemEntities.stream()
                       .map(OrderItemEntity::getTotalAmount)  // Her OrderItem'ın totalAmount'ını alıyoruz
                       .filter(Objects::nonNull)  // Boş Olmayanları filtrele
                       .reduce(BigDecimal.ZERO, BigDecimal::add);  // Hepsini topluyoruz
          }
     }
     @PrePersist
     public void prePersist() {
          this.orderDate = LocalDateTime.now();
          this.deliveryDate = LocalDateTime.now().plusDays(3);
    }

      @PreUpdate
     public void preUpdate(){
          recalculateTotalAmount();
          this.orderDate = LocalDateTime.now();
          this.deliveryDate = LocalDateTime.now().plusDays(3);
    }
}
