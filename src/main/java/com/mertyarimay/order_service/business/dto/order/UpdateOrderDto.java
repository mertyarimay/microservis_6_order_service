package com.mertyarimay.order_service.business.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderDto {
    private int id;
    private int adressId;
}
