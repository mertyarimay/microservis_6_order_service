package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.order.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.order.UpdateOrderDto;

public interface  OrderService {
    CreateOrderDto createOrder(CreateOrderDto createOrderDto,String token);
    ApprovalResponseDto updateOrder(UpdateOrderDto updateOrderDto, int id);
}
