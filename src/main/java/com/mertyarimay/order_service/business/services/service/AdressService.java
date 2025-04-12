package com.mertyarimay.order_service.business.services.service;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;
import com.mertyarimay.order_service.business.dto.adress.GetByIdAdressDto;
import com.mertyarimay.order_service.business.dto.adress.UpdateAdressDto;

public interface AdressService {
    CreateAdressDto create(CreateAdressDto createAdressDto);
    UpdateAdressDto update(UpdateAdressDto updateAdressDto, int id);
    GetByIdAdressDto getById(int id);
    boolean delete(int id);
}
