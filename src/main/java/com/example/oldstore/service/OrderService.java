package com.example.oldstore.service;

import java.util.List;

import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.enums.OrderStatus;

public interface OrderService {
	
	List<OrderDto> getOrdersByUser(Integer userId);
	List<OrderDto> getAllOrders();
	OrderDto getOrderById(Integer orderId);
	OrderDto createOrder(Integer userId, String recipient, String phone, String address);
	void updateOrderStatus(Integer orderId, OrderStatus newStatus, boolean recordShippedTime);
	void simulatePayment(Integer orderId);
	void confirmReceipt(Integer orderId, Integer userId);
	void markOrderAsPaid(String merchantTradeNo);
	void saveMerchantTradeNo(Integer orderId, String merchantTradeNo);
}
