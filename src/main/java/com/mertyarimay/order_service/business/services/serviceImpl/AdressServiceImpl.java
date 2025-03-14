package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;
import com.mertyarimay.order_service.business.services.service.AdressService;
import com.mertyarimay.order_service.data.entity.AdressEntity;
import com.mertyarimay.order_service.data.repository.AdressRepository;
import com.mertyarimay.order_service.mapper.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdressServiceImpl implements AdressService {
    private  final AdressRepository adressRepository;
    private final ModelMapperService modelMapperService;
    @Override
    public CreateAdressDto create(CreateAdressDto createAdressDto) {
        AdressEntity adressEntity=modelMapperService.forRequest().map(createAdressDto,AdressEntity.class);
        adressRepository.save(adressEntity);
        CreateAdressDto createAdress=modelMapperService.forRequest().map(adressEntity,CreateAdressDto.class);
        return createAdress;
    }
}
