package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.UpdateOrderDto;
import com.mertyarimay.order_service.business.services.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("order/api")
public class OrderApi {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody CreateOrderDto createOrderDto){
        CreateOrderDto createOrder=orderService.createOrder(createOrderDto);
        if(createOrder!=null){
            return ResponseEntity.ok("Ürün eklemek İçin Sipariş Kaydınız Oluşturuldu Ürünlerinizi Ekleyebilirsiniz");

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sipariş Kayıt İşleminiz Oluşturulamadı !!!");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateOrderDto updateOrderDto, @PathVariable("id") int id){
        ApprovalResponseDto orderResponse=orderService.updateOrder(updateOrderDto,id);
        if(orderResponse!=null){
            return ResponseEntity.ok(orderResponse);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sipariş Kaydınız ile ilgili bir sorun oluştu onaya gidilemiyor");
        }

    }


}
