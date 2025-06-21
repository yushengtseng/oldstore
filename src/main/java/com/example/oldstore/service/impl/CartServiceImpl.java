package com.example.oldstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.oldstore.exception.ResourceNotFoundException;
import com.example.oldstore.mapper.CartItemMapper;
import com.example.oldstore.model.dto.CartItemDto;
import com.example.oldstore.model.entity.CartItem;
import com.example.oldstore.model.entity.Product;
import com.example.oldstore.model.entity.User;
import com.example.oldstore.repository.CartItemRepository;
import com.example.oldstore.repository.ProductRepository;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartItemMapper cartItemMapper;
	
	@Override
	public void addToCart(Integer userId, Integer productId, Integer quantity) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者 ID:" + userId + "。"));
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到商品 ID:" + productId + "。"));
		
		List<CartItem> existingItems = cartItemRepository.findByUser(user);
		for(CartItem item : existingItems) {
			if(item.getProduct().getProductId().equals(productId)) {
				
				int newQuantity = item.getQuantity() + quantity;
				int stock = product.getStock();
				
				if(newQuantity > stock) {
					newQuantity = stock;
				}
				
				item.setQuantity(newQuantity);
				cartItemRepository.save(item);
				return;
			}
		}
		
		if(quantity > product.getStock()) {
			throw new IllegalArgumentException("加入數量超過庫存數量。");
		}
		
		CartItem newItem = new CartItem();
			newItem.setUser(user);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
		cartItemRepository.save(newItem);
	}

	@Override
	public List<CartItemDto> getUserCart(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者 ID:" + userId + "。"));
		return cartItemRepository.findByUserWithProduct(user).stream()
				.map(cartItemMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void updateCartItemQuantity(Integer cartItemId, Integer quantity) {
		CartItem item = cartItemRepository.findByIdWithProduct(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到購物車項目 ID:" + cartItemId + "。"));
		
		int stock = item.getProduct().getStock();
		
		if(quantity < 1 || quantity > stock) {
			throw new IllegalArgumentException("商品存貨不足。");
		}
		
		item.setQuantity(quantity);
		cartItemRepository.save(item);
	}

	@Override
	public void removeCartItem(Integer cartItemId) {
		if(!cartItemRepository.existsById(cartItemId)) {
			throw new ResourceNotFoundException("找不到購物車項目 ID:" + cartItemId + "。");
		}
		cartItemRepository.deleteById(cartItemId);
	}

	@Override
	@Transactional
	public void clearCart(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者 ID:" + userId + "。"));
		cartItemRepository.deleteByUser(user);
	}
}
