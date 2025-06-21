package com.example.oldstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oldstore.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

}
