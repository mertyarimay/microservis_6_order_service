package com.mertyarimay.order_service.business.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdOrderItemDto {
    private int id;
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal totalAmount;

}
