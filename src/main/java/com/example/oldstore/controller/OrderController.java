package com.example.oldstore.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.oldstore.model.dto.CartItemDto;
import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.service.CartService;
import com.example.oldstore.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;
	
	// 顯示結帳頁面
	@GetMapping("/checkout")
	public String checkoutPage(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<CartItemDto> cartItems = cartService.getUserCart(userId);
		
		if(cartItems.isEmpty()) {
			model.addAttribute("message", "購物車是空的。");
			return "redirect:/cart";
		}
		
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("shippingFee", new BigDecimal("80")); // 傳入固定運費 80
		return "order/checkout";
	}
	
	// 處理訂單提交
	@PostMapping("/submit")
	public String submitOrder(@RequestParam String recipient,
			                  @RequestParam String phone,
			                  @RequestParam String address,
			                  HttpSession session,
							  Model model) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		OrderDto order = orderService.createOrder(userId, recipient, phone, address);
		
		session.setAttribute("lastOrder", order); // 暫存訂單資訊，用於確認頁
		return "redirect:/order/confirm";
	}
	
	// 訂單完成確認頁
	@GetMapping("/confirm")
	public String confirmPage(HttpSession session, Model model) {
		OrderDto order = (OrderDto) session.getAttribute("lastOrder"); 
		if(order == null) {
			return "redirect:/home"; // 防止直接進入
		}
		
		model.addAttribute("order", order);
		session.removeAttribute("lastOrder"); // 顯示後，即移除
		return "order/confirm";
	}
	
	// 訂單歷史紀錄
	@GetMapping("/history")
	public String orderHistory(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<OrderDto> orders = orderService.getOrdersByUser(userId);
		model.addAttribute("orders", orders);
		return "order/history";
	}
	
	// 單筆訂單詳細內容
	@GetMapping("/detail/{orderId}")
	public String orderDetail(@PathVariable Integer orderId, HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		OrderDto order = orderService.getOrderById(orderId);
		
		if(order == null || !order.getUserId().equals(userId)) {
			return "redirect:/order/history"; // 防止查詢他人訂單
		}
		
		model.addAttribute("order", order);
		return "order/detail";
	}
	
	// 結帳
	@PostMapping("/pay/{orderId}")
	public String simulatePayment(@PathVariable Integer orderId, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		OrderDto order = orderService.getOrderById(orderId);
		
		if(!order.getUserId().equals(userId)) {
			return "redirect:/order/history"; // 防止操作他人訂單
		}
		
		orderService.simulatePayment(orderId);
		return "redirect:/order/history";
	}
	
	// 用戶確認收貨
	@PutMapping("/detail/{orderId}")
	public String confirmReceipt(@PathVariable Integer orderId, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		orderService.confirmReceipt(orderId, userId);
		return "redirect:/order/detail/" + orderId;
	}
}
