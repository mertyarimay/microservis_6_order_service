package com.mertyarimay.order_service.clıent;

import com.mertyarimay.order_service.business.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",url="http://localhost:8082/product/api")
public interface ProductClient {
    @GetMapping("/getById/{id}")
    ProductDto getProductById(@PathVariable("id") int id);
}
