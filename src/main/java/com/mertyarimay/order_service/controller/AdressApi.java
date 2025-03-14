package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;
import com.mertyarimay.order_service.business.services.service.AdressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("adress/api")
public class AdressApi {
    private final AdressService adressService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateAdressDto createAdressDto){
        CreateAdressDto createAdress=adressService.create(createAdressDto);
        if(createAdress!=null){
            return ResponseEntity.ok("Adres kayıt işlemi başarılı sipariş onayla kısmına geçebilirsiniz");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Adres Kayıt İşlemi Başarısız");
    }
}
