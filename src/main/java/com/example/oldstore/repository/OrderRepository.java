package com.example.oldstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.oldstore.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("""
		    SELECT DISTINCT o FROM Order o
		    JOIN FETCH o.user
		    JOIN FETCH o.items i
		    JOIN FETCH i.product
		    WHERE o.user.userId = :userId
		""")
		List<Order> findByUserIdWithItemsAndProducts(@Param("userId") Integer userId);

	@Query("""
			SELECT o FROM Order o
			JOIN FETCH o.user
			JOIN FETCH o.items i
			JOIN FETCH i.product
			WHERE o.orderId = :orderId
		""")
	Optional<Order> findByIdWithItems(@Param("orderId") Integer orderId);
	
	@Query("""
			SELECT DISTINCT o FROM Order o
			JOIN FETCH o.user
			JOIN FETCH o.items i
			JOIN FETCH i.product
			ORDER BY o.orderDate DESC
			""")
	List<Order> findAllWithUser();
}
