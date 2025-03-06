package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.CreateOrderItemDto;
import com.mertyarimay.order_service.business.dto.ProductDto;
import com.mertyarimay.order_service.business.services.service.OrderItemService;
import com.mertyarimay.order_service.clıent.ProductClient;
import com.mertyarimay.order_service.data.entity.OrderEntity;
import com.mertyarimay.order_service.data.entity.OrderItemEntity;
import com.mertyarimay.order_service.data.repository.OrderItemRepository;
import com.mertyarimay.order_service.data.repository.OrderRepository;
import com.mertyarimay.order_service.mapper.ModelMapperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final ProductClient productClient;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapperService modelMapperService;

    @Transactional
    @Override
    public CreateOrderItemDto create(CreateOrderItemDto createOrderItemDto) {
        ProductDto product=productClient.getProductById(createOrderItemDto.getProductId());
        OrderEntity orderEntity=orderRepository.findById(createOrderItemDto.getOrderId()).orElse(null);
        if(orderEntity!=null){
            createOrderItemDto.setOrderId(orderEntity.getId());
            createOrderItemDto.setProductId(product.getId());
            createOrderItemDto.setProductName(product.getProductName());
            BigDecimal price=new BigDecimal(product.getProductPrice());
            createOrderItemDto.setProductPrice(price);
            OrderItemEntity orderItemEntity=modelMapperService.forRequest().map(createOrderItemDto,OrderItemEntity.class);
            orderItemEntity.setOrderEntity(orderEntity);
            orderItemRepository.save(orderItemEntity);
            orderRepository.updateStatus("Active",orderItemEntity.getOrderEntity().getId());
            CreateOrderItemDto createOrderItem=modelMapperService.forRequest().map(orderItemEntity, CreateOrderItemDto.class);
            createOrderItem.setOrderId(orderItemEntity.getOrderEntity().getId());
            return createOrderItem;

        }
        return null;


    }
}
