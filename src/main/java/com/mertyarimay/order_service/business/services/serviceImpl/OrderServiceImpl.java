package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.*;

import com.mertyarimay.order_service.business.dto.order.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetAllDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetByIdDto;
import com.mertyarimay.order_service.business.dto.order.UpdateOrderDto;
import com.mertyarimay.order_service.business.dto.orderItem.OrderInProductDto;
import com.mertyarimay.order_service.business.services.service.OrderService;
import com.mertyarimay.order_service.clıent.CustomerClient;
import com.mertyarimay.order_service.clıent.ProductClient;
import com.mertyarimay.order_service.data.entity.AdressEntity;
import com.mertyarimay.order_service.data.entity.OrderEntity;
import com.mertyarimay.order_service.data.repository.AdressRepository;
import com.mertyarimay.order_service.data.repository.OrderItemRepository;
import com.mertyarimay.order_service.data.repository.OrderRepository;
import com.mertyarimay.order_service.mapper.ModelMapperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final CustomerClient customerClient;


    @Transactional
    @Override
    public CreateOrderDto createOrder(CreateOrderDto createOrderDto, String token) {
        OrderEntity order=new OrderEntity();
        CustomerDto customerDto=customerClient.getCustomerId(createOrderDto.getCustomerId(),token);
        order.setCustomerId(customerDto.getId());
        order.setCustomerName(customerDto.getName());
        order.setCustomerLastName(customerDto.getLastName());
        order.setCustomerPhoneNumber(customerDto.getPhoneNumber());
        order.setCustomerEmail(customerDto.getEmail());
        orderRepository.save(order);
        CreateOrderDto createOrder=modelMapperService.forRequest().map(order,CreateOrderDto.class);
        return createOrder;
    }

    @Override
    public List<OrderGetAllDto> getAll() {
        List<OrderEntity>orders=orderRepository.findAll();
        List<OrderGetAllDto>orderGetAllDtos=orders.stream().map(orderEntity -> {
            OrderGetAllDto orderGetAllDto=new OrderGetAllDto();
            orderGetAllDto.setCustomerName(orderEntity.getCustomerName());
            orderGetAllDto.setCustomerLastName(orderEntity.getCustomerLastName());
            List<Object>orderItems=orderEntity.getOrderItemEntities().stream().map(orderItemEntity -> {
                OrderInProductDto orderInProductDto=new OrderInProductDto();
                orderInProductDto.setProductName(orderItemEntity.getProductName());
                orderInProductDto.setQuantity(orderItemEntity.getQuantity());
                return orderInProductDto;
            }).collect(Collectors.toList());
              orderGetAllDto.setOrderItems(orderItems);
              orderGetAllDto.setTotalAmount(orderEntity.getTotalAmount());
              orderGetAllDto.setDeliveryDate(orderEntity.getDeliveryDate());
              return orderGetAllDto;
        }).collect(Collectors.toList());

        return orderGetAllDtos;


    }



    @Override
    public OrderGetByIdDto getById(int id) {
        OrderEntity order=orderRepository.findById(id).orElse(null);
        if(order!=null){
            OrderGetByIdDto orderGetByIdDto=new OrderGetByIdDto();
            orderGetByIdDto.setCustomerName(order.getCustomerName());
            orderGetByIdDto.setCustomerLastName(order.getCustomerLastName());
            List<Object>orderItems=order.getOrderItemEntities().stream().map(orderItemEntity -> {
                OrderInProductDto orderInProductDto=new OrderInProductDto();
                orderInProductDto.setProductName(orderItemEntity.getProductName());
                orderInProductDto.setQuantity(orderItemEntity.getQuantity());
                return orderInProductDto;
            }).collect(Collectors.toList());
            orderGetByIdDto.setOrderItems(orderItems);
            orderGetByIdDto.setTotalAmount(order.getTotalAmount());
            orderGetByIdDto.setDeliveryDate(order.getDeliveryDate());
            return orderGetByIdDto;
        }
        else {
            return null;
        }
    }


    @Transactional
    @Override
    public ApprovalResponseDto updateOrder(UpdateOrderDto updateOrderDto, int id) {
        OrderEntity order=orderRepository.findById(id).orElse(null);
        if(order!=null){
            AdressEntity adressEntity=adressRepository.findById(updateOrderDto.getAdressId()).orElse(null);
            if(adressEntity!=null){
                order.setAdressEntity(adressEntity);
                order.recalculateTotalAmount();
                order.preUpdate();
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
    @Transactional
    @Override
    public boolean delete(int id) {
        OrderEntity order=orderRepository.findById(id).orElse(null);
        if(order!=null){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
