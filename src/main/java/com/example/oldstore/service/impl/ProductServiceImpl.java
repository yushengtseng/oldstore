package com.example.oldstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.oldstore.exception.ProductNotFoundException;
import com.example.oldstore.mapper.ProductMapper;
import com.example.oldstore.model.dto.ProductDto;
import com.example.oldstore.model.entity.Product;
import com.example.oldstore.repository.ProductRepository;
import com.example.oldstore.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> getAllProducts() {
    	System.out.println("hello");
        return productRepository.findAll()
        		.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("找不到商品 ID：" + id + "。"));
        return productMapper.toDto(product);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Integer id, ProductDto productDto) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("無法更新，找不到商品 ID：" + id + "。");
        }
        Product updatedProduct = productMapper.toEntity(productDto);
        updatedProduct.setProductId(id);
        productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("無法刪除，找不到商品 ID：" + id + "。");
        }
        productRepository.deleteById(id);
    }
}