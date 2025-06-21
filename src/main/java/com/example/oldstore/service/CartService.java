package com.example.oldstore.service;

import java.util.List;
import com.example.oldstore.model.dto.CartItemDto;

public interface CartService {
	
	void addToCart(Integer userId, Integer productId, Integer quantity);
	List<CartItemDto> getUserCart(Integer userId);
	void updateCartItemQuantity(Integer cartItemId, Integer quantity);
	void removeCartItem(Integer cartItemId);
	void clearCart(Integer userId);
}
