package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.order.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetAllDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetByIdDto;
import com.mertyarimay.order_service.business.dto.order.UpdateOrderDto;

import java.util.List;

public interface  OrderService {
    CreateOrderDto createOrder(CreateOrderDto createOrderDto,String token);
    List<OrderGetAllDto > getAll();
    OrderGetByIdDto getById(int id);
    ApprovalResponseDto updateOrder(UpdateOrderDto updateOrderDto, int id);
    boolean delete(int id);

}
