package com.mertyarimay.order_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private int id;
    private String productName;
    private double productPrice;
    private String description;
    private String colour;
    private String brandName;
    private int stockQuantity;
}
