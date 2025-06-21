package com.example.oldstore.model.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
	
	private Integer productId;
	private String productName; // 顯示用
	private Integer quantity;
	private BigDecimal price;
}
