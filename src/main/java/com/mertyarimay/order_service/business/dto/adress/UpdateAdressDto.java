package com.mertyarimay.order_service.business.dto.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateAdressDto {
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String street;
    @Size(min = 1)
    @NotNull
    @NotBlank
    private String city;
    @Size(min = 1)
    @NotNull
    @NotBlank
    private  String country;
}
