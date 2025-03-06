package com.mertyarimay.order_service.data.repository;

import com.mertyarimay.order_service.data.entity.AdressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<AdressEntity,Integer> {
}
