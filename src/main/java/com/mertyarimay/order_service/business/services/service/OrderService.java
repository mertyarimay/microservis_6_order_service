package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.UpdateOrderDto;

public interface  OrderService {
    CreateOrderDto createOrder(CreateOrderDto createOrderDto);
    ApprovalResponseDto updateOrder(UpdateOrderDto updateOrderDto, int id);
}
