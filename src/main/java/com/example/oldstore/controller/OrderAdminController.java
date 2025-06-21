package com.example.oldstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oldstore.exception.ResourceNotFoundException;
import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.enums.OrderStatus;
import com.example.oldstore.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

	@Autowired
	private OrderService orderService;
	
	// 顯示所有訂單
	@GetMapping
	public String listAllOrders(Model model) {
		List<OrderDto> orders = orderService.getAllOrders();
		model.addAttribute("orders", orders);
		return "admin/order/order_list";
	}
	
	// 顯示訂單明細
	@GetMapping("/detail/{orderId}")
	public String showOrderDetail(@PathVariable Integer orderId, Model model) {
		OrderDto order = orderService.getOrderById(orderId);
		if(order == null) {
			throw new ResourceNotFoundException("找不到訂單 ID:" + orderId + "。");
		}
		model.addAttribute("order", order);
		return "admin/order/order_detail";
	}
	
	@PutMapping("/update-status/{orderId}/{status}")
	public String updateStatus(@PathVariable Integer orderId,@PathVariable String status) {
		OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
		boolean recordShippedTime = (newStatus == OrderStatus.SHIPPED);
		
		orderService.updateOrderStatus(orderId, newStatus, recordShippedTime);
		return "redirect:/admin/orders/detail/" + orderId;
	}
}
