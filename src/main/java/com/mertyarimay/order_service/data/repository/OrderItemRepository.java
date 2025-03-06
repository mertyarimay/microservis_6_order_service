package com.mertyarimay.order_service.data.repository;

import com.mertyarimay.order_service.data.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Integer> {
}
