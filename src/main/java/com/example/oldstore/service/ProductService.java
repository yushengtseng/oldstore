package com.example.oldstore.service;

import java.util.List;

import com.example.oldstore.model.dto.ProductDto;

public interface ProductService {

	List<ProductDto> getAllProducts();
	ProductDto getProductById(Integer id);
	void createProduct(ProductDto productDto);
	void updateProduct(Integer id, ProductDto productDto);
	void deleteProduct(Integer id);
}
