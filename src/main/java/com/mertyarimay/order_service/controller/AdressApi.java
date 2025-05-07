package com.mertyarimay.order_service.controller;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;
import com.mertyarimay.order_service.business.dto.adress.GetByIdAdressDto;
import com.mertyarimay.order_service.business.dto.adress.UpdateAdressDto;
import com.mertyarimay.order_service.business.services.service.AdressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("adress/api")
public class AdressApi {
    private final AdressService adressService;

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateAdressDto createAdressDto){
        CreateAdressDto createAdress=adressService.create(createAdressDto);
        if(createAdress!=null){
            return ResponseEntity.ok("Adres kayıt işlemi başarılı sipariş onayla kısmına geçebilirsiniz");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Adres Kayıt İşlemi Başarısız");
    }
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getByID(@PathVariable("id")int id){
        GetByIdAdressDto getByIdAdressDto=adressService.getById(id);
        if(getByIdAdressDto!=null){
            return ResponseEntity.ok(getByIdAdressDto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Bulunamadı");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateAdressDto updateAdressDto,@PathVariable("id")int id){
        UpdateAdressDto updateAdress=adressService.update(updateAdressDto,id);
        if(updateAdress!=null){
            return ResponseEntity.ok("Adres Güncelleme İşleminiz Başarılı");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncellemek istediğiniz adress bulunamadı");
        }
    }


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id)  {
        boolean delete=adressService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Adress Kaydı silinmiştir");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adres Kaydı bulunamadı Silme İşlemi Başarısız");

    }

}
