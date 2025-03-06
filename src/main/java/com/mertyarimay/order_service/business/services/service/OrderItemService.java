package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.CreateOrderItemDto;

public interface OrderItemService {
    CreateOrderItemDto create(CreateOrderItemDto createOrderItemDto);
}
