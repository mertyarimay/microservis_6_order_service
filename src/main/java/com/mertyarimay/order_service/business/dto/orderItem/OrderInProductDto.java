package com.mertyarimay.order_service.business.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInProductDto {
    private String productName;
    private int quantity;

}
