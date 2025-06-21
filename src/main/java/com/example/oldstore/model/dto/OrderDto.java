package com.example.oldstore.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.oldstore.model.enums.OrderStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

	private Integer orderId;
	private Integer userId;
	private BigDecimal shippingFee;
	private BigDecimal totalPrice;
	private OrderStatus status;
	private String recipient;
	private String phone;
	private String address;
	private List<OrderItemDto> items;
	private String username;
	
	private LocalDateTime orderDate;
	public String getFormattedOrderDate() {
		return this.orderDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	private LocalDateTime paidAt;
	public String getFormattedPaidAt() {
		if(paidAt == null) return null;
		return this.paidAt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	private LocalDateTime shippedAt;
	public String getFormattedShippedAt() {
		if(shippedAt == null) return null;
		return this.shippedAt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	private LocalDateTime receivedAt;
	public String getFormattedReceivedAt() {
		if(receivedAt == null) return null;
		return this.receivedAt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
}
