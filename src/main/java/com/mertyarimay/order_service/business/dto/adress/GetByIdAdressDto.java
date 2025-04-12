package com.mertyarimay.order_service.business.dto.adress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetByIdAdressDto {
    private int id;
    private String street;
    private String city;
    private  String country;

}
