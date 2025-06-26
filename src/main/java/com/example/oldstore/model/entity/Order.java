package com.example.oldstore.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.oldstore.model.enums.OrderStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	private LocalDateTime orderDate;
	
	private LocalDateTime paidAt;
	
	private LocalDateTime shippedAt;
	
	private LocalDateTime receivedAt;
	
	private BigDecimal shippingFee; // 運費欄位
	
	private BigDecimal totalPrice;
	
	@Column(length = 30)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private String recipient;
	
	private String phone;
	
	private String address;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items;
	
	@Column(length = 50, unique = true)
	private String merchantTradeNo;

}
