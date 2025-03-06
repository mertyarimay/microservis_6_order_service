package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.CreateOrderItemDto;
import com.mertyarimay.order_service.business.services.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("order/item/api")
public class OrderItemApi {
    private final OrderItemService orderItemService;
    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody CreateOrderItemDto createOrderItemDto){
        CreateOrderItemDto createOrderItem=orderItemService.create(createOrderItemDto);
        if(createOrderItem!=null){
            return  ResponseEntity.ok("Ürününüz sepete eklenmiştir Teslimat adresi ekleme kısmına geçebilirsiniz");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ürün sepete eklenme işlemi başarısız Sipariş oluşturunuz Lütfen");
        }
    }
}
