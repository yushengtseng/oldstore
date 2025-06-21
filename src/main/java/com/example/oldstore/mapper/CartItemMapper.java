package com.example.oldstore.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.oldstore.model.dto.CartItemDto;
import com.example.oldstore.model.entity.CartItem;

@Component
public class CartItemMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public CartItemDto toDto(CartItem cartItem) {
		CartItemDto dto = new CartItemDto();
		dto.setCartItemId(cartItem.getCartItemId());
		dto.setQuantity(cartItem.getQuantity());
		dto.setUserId(cartItem.getUser().getUserId());
		dto.setProductId(cartItem.getProduct().getProductId());
		dto.setProductName(cartItem.getProduct().getName());
		dto.setPrice(cartItem.getProduct().getPrice());
		dto.setImagePath(cartItem.getProduct().getImagePath());
		dto.setStock(cartItem.getProduct().getStock());
		return dto;
	}
	
	public CartItem toEntity(CartItemDto dto) {
		return modelMapper.map(dto, CartItem.class);
	}
}
