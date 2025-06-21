package com.example.oldstore.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CartItemDto {
	
	private Integer cartItemId;
	private Integer userId;
	private Integer productId;
	private String productName;
	private String imagePath;
	private Integer quantity;
	private BigDecimal price;
	private LocalDateTime addedAt;
	private Integer stock;
}
