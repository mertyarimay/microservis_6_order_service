package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;

public interface AdressService {
    CreateAdressDto create(CreateAdressDto createAdressDto);
}
