package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.CreateOrderDto;

import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.OrderInProductDto;
import com.mertyarimay.order_service.business.dto.UpdateOrderDto;
import com.mertyarimay.order_service.business.services.service.OrderService;
import com.mertyarimay.order_service.clıent.ProductClient;
import com.mertyarimay.order_service.data.entity.AdressEntity;
import com.mertyarimay.order_service.data.entity.OrderEntity;
import com.mertyarimay.order_service.data.entity.OrderItemEntity;
import com.mertyarimay.order_service.data.repository.AdressRepository;
import com.mertyarimay.order_service.data.repository.OrderItemRepository;
import com.mertyarimay.order_service.data.repository.OrderRepository;
import com.mertyarimay.order_service.mapper.ModelMapperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapperService modelMapperService;
    private final AdressRepository adressRepository;
    private final ProductClient productClient;


    @Transactional
    @Override
    public CreateOrderDto createOrder(CreateOrderDto createOrderDto) {
        OrderEntity order=new OrderEntity();
        order.setCustomerId(createOrderDto.getCustomerId());
        orderRepository.save(order);

        CreateOrderDto createOrder=modelMapperService.forRequest().map(order,CreateOrderDto.class);
        return createOrder;
    }

    @Override
    public ApprovalResponseDto updateOrder(UpdateOrderDto updateOrderDto, int id) {
        OrderEntity order=orderRepository.findById(id).orElse(null);
        if(order!=null){
            AdressEntity adressEntity=adressRepository.findById(updateOrderDto.getAdressId()).orElse(null);
            if(adressEntity!=null){
                order.setAdressEntity(adressEntity);
                orderRepository.save(order);
                ApprovalResponseDto approvalResponse=new ApprovalResponseDto();
               List<Object>products= order.getOrderItemEntities().stream().map(orderItemEntity -> {
                    OrderInProductDto orderInProductDto=new OrderInProductDto();
                    orderInProductDto.setProductName(orderItemEntity.getProductName());
                    orderInProductDto.setQuantity(orderItemEntity.getQuantity());
                    return orderInProductDto;
                }).collect(Collectors.toList());
                approvalResponse.setProduct(products);
                approvalResponse.setTotalAmount(order.getTotalAmount());
                approvalResponse.setDeliveryDate(order.getDeliveryDate());
                approvalResponse.setStreet(order.getAdressEntity().getStreet());
                approvalResponse.setCity(order.getAdressEntity().getCity());
                approvalResponse.setCountry(order.getAdressEntity().getCountry());

                return approvalResponse;
            }
        }
        return null;

    }
}
