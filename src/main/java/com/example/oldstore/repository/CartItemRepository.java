package com.example.oldstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.oldstore.model.entity.CartItem;
import com.example.oldstore.model.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	
	List<CartItem> findByUser(User user);
	void deleteByUser(User user);

	@Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.user = :user")
	List<CartItem> findByUserWithProduct(@Param("user") User user);
	
	@Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.cartItemId = :cartItemId")
	Optional<CartItem> findByIdWithProduct(@Param("cartItemId") Integer cartItemId);
}
