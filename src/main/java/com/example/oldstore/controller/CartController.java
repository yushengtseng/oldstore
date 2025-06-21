package com.example.oldstore.controller;

import java.util.List;

import com.example.oldstore.model.dto.CartItemDto;
import com.example.oldstore.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	// 顯示購物車內容
	@GetMapping
	public String showCart(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<CartItemDto> cartItems = cartService.getUserCart(userId);
		model.addAttribute("cartItems", cartItems);
			return "cart/cart";
		}
	
	// 加入購物車
	@PostMapping("/add")
	public String addToCart(@RequestParam Integer productId,
							HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		cartService.addToCart(userId, productId, 1);
		return "redirect:/shop/products?success=1";
	}
	
	// 修改數量
	@PutMapping("/update")
	public String updateQuantity(@RequestParam Integer cartItemId,
	                             @RequestParam Integer quantity) {
	    cartService.updateCartItemQuantity(cartItemId, quantity);
	    return "redirect:/cart";
	}
	
	// 刪除項目
	@DeleteMapping("/delete")
	public String deleteItem(@RequestParam Integer cartItemId) {
		cartService.removeCartItem(cartItemId);
		return "redirect:/cart";
	}
	
	// 清除購物車
	@PostMapping("/clear")
	public String clearCart(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		cartService.clearCart(userId);
		return "redirect:/cart";
	}
}

