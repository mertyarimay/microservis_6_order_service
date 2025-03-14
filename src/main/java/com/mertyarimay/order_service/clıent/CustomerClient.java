package com.mertyarimay.order_service.clıent;

import com.mertyarimay.order_service.business.dto.CustomerDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service",url="http://localhost:8081/customer/api")
public interface CustomerClient {

    @GetMapping("/getById/{id}")
    CustomerDto getCustomerId(@PathVariable("id") int id,
                              @RequestHeader("Authorization") String token);
}
