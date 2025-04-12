package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.order.CreateOrderDto;
import com.mertyarimay.order_service.business.dto.ApprovalResponseDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetAllDto;
import com.mertyarimay.order_service.business.dto.order.OrderGetByIdDto;
import com.mertyarimay.order_service.business.dto.order.UpdateOrderDto;
import com.mertyarimay.order_service.business.services.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("order/api")
public class OrderApi {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody CreateOrderDto createOrderDto ,@RequestHeader("Authorization") String token){
        CreateOrderDto createOrder=orderService.createOrder(createOrderDto,token);
        if(createOrder!=null){
            return ResponseEntity.ok("Ürün eklemek İçin Sipariş Kaydınız Oluşturuldu Ürünlerinizi Ekleyebilirsiniz");

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sipariş Kayıt İşleminiz Oluşturulamadı !!!");
        }
    }
    @GetMapping("/getAll")
    public List<OrderGetAllDto>getAll(){
        List<OrderGetAllDto>orderGetAllDtos=orderService.getAll();
        return orderGetAllDtos;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        OrderGetByIdDto orderGetByIdDto=orderService.getById(id);
        if(orderGetByIdDto!=null){
            return ResponseEntity.ok(orderGetByIdDto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Bulunamadı");
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id")int id){
        boolean delete=orderService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Silme işlemi Başarılı");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silmek istediğiniz order bulunamadı");
        }
    }



}
