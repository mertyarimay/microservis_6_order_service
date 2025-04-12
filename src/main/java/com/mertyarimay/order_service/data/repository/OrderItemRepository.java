package com.mertyarimay.order_service.data.repository;

import com.mertyarimay.order_service.data.entity.OrderEntity;
import com.mertyarimay.order_service.data.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Integer> {
    List<OrderItemEntity>findByOrderEntity(OrderEntity order);

}


