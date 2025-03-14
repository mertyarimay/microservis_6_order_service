package com.mertyarimay.order_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
}
