package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.orderItem.CreateOrderItemDto;
import com.mertyarimay.order_service.business.dto.ProductDto;
import com.mertyarimay.order_service.business.dto.orderItem.GetByIdOrderItemDto;
import com.mertyarimay.order_service.business.dto.orderItem.UpdateOrderItemDto;
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
        ProductDto product = productClient.getProductById(createOrderItemDto.getProductId());
        OrderEntity orderEntity = orderRepository.findById(createOrderItemDto.getOrderId()).orElse(null);
        if (orderEntity != null) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setProductId(product.getId());
            orderItemEntity.setProductName(product.getProductName());
            orderItemEntity.setOrderEntity(orderEntity);
            BigDecimal price = new BigDecimal(product.getProductPrice());
            orderItemEntity.setPrice(price);
            orderItemEntity.setQuantity(createOrderItemDto.getQuantity());
            orderItemRepository.save(orderItemEntity);
            orderRepository.updateStatus("Active", orderItemEntity.getOrderEntity().getId());
            CreateOrderItemDto createOrderItem = modelMapperService.forRequest().map(orderItemEntity, CreateOrderItemDto.class);
            createOrderItem.setOrderId(orderItemEntity.getOrderEntity().getId());
            return createOrderItem;

        }
        return null;


    }

    @Override
    public GetByIdOrderItemDto getById(int id) {
        OrderItemEntity orderItemEntity=orderItemRepository.findById(id).orElse(null);
        if(orderItemEntity!=null){
            GetByIdOrderItemDto getByIdOrderItemDto=modelMapperService.forResponse().map(orderItemEntity, GetByIdOrderItemDto.class);
            return getByIdOrderItemDto;
        }else {
            return null;
        }
    }



    @Transactional
    @Override
    public UpdateOrderItemDto update(UpdateOrderItemDto updateOrderItemDto, int id) {
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id).orElse(null);
        if (orderItemEntity != null) {
            ProductDto productDto = productClient.getProductById(updateOrderItemDto.getProductId());
            orderItemEntity.setProductId(productDto.getId());
            orderItemEntity.setProductName(productDto.getProductName());
            BigDecimal price = new BigDecimal(productDto.getProductPrice());
            orderItemEntity.setPrice(price);
            orderItemEntity.setQuantity(updateOrderItemDto.getQuantity());

            orderItemRepository.save(orderItemEntity);
            UpdateOrderItemDto updateOrderItem = modelMapperService.forRequest().map(orderItemEntity, UpdateOrderItemDto.class);
            return updateOrderItem;
        }
        return null;

    }

    @Transactional
    @Override
    public boolean delete(int id) {
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id).orElse(null);

        if (orderItemEntity != null) {
            OrderEntity order = orderItemEntity.getOrderEntity();

            // Order içindeki listeden item'ı çıkar
            if (order != null && order.getOrderItemEntities() != null) {
                order.getOrderItemEntities().removeIf(item -> item.getId() == id);  //RemoveIf metodu  bir koşula göre silme yapar

                // Hibernate orphanRemoval devreye girsin diye sadece order'ı save et
                orderRepository.save(order);

                // Liste boşsa, order'ı da sil
                if (order.getOrderItemEntities().isEmpty()) {
                    orderRepository.delete(order);
                }
            }

            // item silindi mi kontrolü
            return !orderItemRepository.existsById(id);
        }

        return false;
    }



}


