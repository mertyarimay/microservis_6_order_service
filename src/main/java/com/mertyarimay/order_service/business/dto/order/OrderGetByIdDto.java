package com.mertyarimay.order_service.business.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderGetByIdDto {
    private String customerName;
    private String customerLastName;
    private List<Object>orderItems;
    private BigDecimal totalAmount;
    private LocalDateTime deliveryDate;

}
