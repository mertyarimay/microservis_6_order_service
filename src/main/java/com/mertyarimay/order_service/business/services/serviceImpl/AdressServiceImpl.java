package com.mertyarimay.order_service.business.services.serviceImpl;

import com.mertyarimay.order_service.business.dto.adress.CreateAdressDto;
import com.mertyarimay.order_service.business.dto.adress.GetByIdAdressDto;
import com.mertyarimay.order_service.business.dto.adress.UpdateAdressDto;
import com.mertyarimay.order_service.business.services.service.AdressService;
import com.mertyarimay.order_service.data.entity.AdressEntity;
import com.mertyarimay.order_service.data.repository.AdressRepository;
import com.mertyarimay.order_service.mapper.ModelMapperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdressServiceImpl implements AdressService {
    private  final AdressRepository adressRepository;
    private final ModelMapperService modelMapperService;
    @Transactional
    @Override
    public CreateAdressDto create(CreateAdressDto createAdressDto) {
        AdressEntity adressEntity=modelMapperService.forRequest().map(createAdressDto,AdressEntity.class);
        adressRepository.save(adressEntity);
        CreateAdressDto createAdress=modelMapperService.forRequest().map(adressEntity,CreateAdressDto.class);
        return createAdress;
    }

    @Override
    public UpdateAdressDto update(UpdateAdressDto updateAdressDto, int id) {
        AdressEntity adressEntity=adressRepository.findById(id).orElse(null);
        if(adressEntity!=null){
            adressEntity.setStreet(updateAdressDto.getStreet());
            adressEntity.setCity(updateAdressDto.getCity());
            adressEntity.setCountry(updateAdressDto.getCountry());
            adressRepository.save(adressEntity);
            UpdateAdressDto updateAdress=modelMapperService.forRequest().map(adressEntity,UpdateAdressDto.class);
            return updateAdress;
        }
        else {
            return null;
        }
    }

    @Override
    public GetByIdAdressDto getById(int id) {
        AdressEntity adressEntity=adressRepository.findById(id).orElse(null);
        if(adressEntity!=null){
            GetByIdAdressDto getByIdAdressDto=modelMapperService.forResponse().map(adressEntity,GetByIdAdressDto.class);
            return getByIdAdressDto;
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        AdressEntity adressEntity=adressRepository.findById(id).orElse(null);
        if(adressEntity!=null){
            adressRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
