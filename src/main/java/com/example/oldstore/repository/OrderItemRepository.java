package com.example.oldstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.oldstore.model.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
