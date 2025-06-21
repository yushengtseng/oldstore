package com.example.oldstore.exception;

public class ProductNotFoundException extends RuntimeException {
    
	public ProductNotFoundException() {
        super("找不到指定的商品");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}