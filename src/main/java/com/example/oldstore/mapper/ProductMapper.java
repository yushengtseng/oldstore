package com.example.oldstore.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.oldstore.model.dto.ProductDto;
import com.example.oldstore.model.entity.Product;

@Component // 此物件由 Springboot 來管理
public class ProductMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProductDto toDto(Product product) {
		// Entity 轉 DTO
		return modelMapper.map(product, ProductDto.class);
	}
	
	public Product toEntity(ProductDto productDto) {
		// DTO 轉 Entity
		return modelMapper.map(productDto, Product.class);
	}
}