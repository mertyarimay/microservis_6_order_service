package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.orderItem.CreateOrderItemDto;
import com.mertyarimay.order_service.business.dto.orderItem.UpdateOrderItemDto;

public interface OrderItemService {
    CreateOrderItemDto create(CreateOrderItemDto createOrderItemDto);
    UpdateOrderItemDto update(UpdateOrderItemDto updateOrderItemDto,int id);
    boolean delete(int id);
}
