package com.mertyarimay.order_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalResponseDto {
    private List<Object>product;
    private BigDecimal totalAmount;
    private LocalDateTime deliveryDate;
    private String street;
    private  String city;
    private String country;


}
