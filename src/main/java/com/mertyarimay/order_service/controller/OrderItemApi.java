package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.orderItem.CreateOrderItemDto;
import com.mertyarimay.order_service.business.dto.orderItem.GetByIdOrderItemDto;
import com.mertyarimay.order_service.business.dto.orderItem.UpdateOrderItemDto;
import com.mertyarimay.order_service.business.services.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ürün sepete eklenme işlemi başarısız Sipariş kaydı oluşturunuz Lütfen");
        }
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id")int id){
        GetByIdOrderItemDto getByIdOrderItemDto=orderItemService.getById(id);
        if(getByIdOrderItemDto!=null){
            return ResponseEntity.ok(getByIdOrderItemDto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Bulunamadı");
        }
    }





    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody UpdateOrderItemDto updateOrderItemDto,@PathVariable("id")int id){
        UpdateOrderItemDto updateOrderItem=orderItemService.update(updateOrderItemDto,id);
        if(updateOrderItem!=null){
            return ResponseEntity.ok("Sepetteki ürünün güncellemesi başarılı bir şekilde gerçekleşti ");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sepetteki ürün güncelleme işlemi başarısız");
        }

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id")int id){
        boolean delete= orderItemService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Ürünü sepetten silme işlemi başarılı ");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silmek isteğiniz ürün sepetinizde bulunamadı Silme işlemi BAŞARISIZ...");
        }
    }


}
