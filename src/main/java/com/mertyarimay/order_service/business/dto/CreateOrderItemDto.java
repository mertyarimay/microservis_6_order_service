package com.mertyarimay.order_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemDto {
    private int id;
    private int orderId;
    private  int productId;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;

}
