package com.mertyarimay.order_service.data.repository;

import com.mertyarimay.order_service.data.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    @Modifying
    @Query("Update OrderEntity o SET o.status=:status where o.id=:id")
     void updateStatus(@Param("status")String status,@Param("id") int id);

}
