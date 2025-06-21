package com.example.oldstore.service.impl;

import java.math.BigDecimal;
import java.nio.channels.AcceptPendingException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.oldstore.exception.ResourceNotFoundException;
import com.example.oldstore.exception.UnauthorizedActionException;
import com.example.oldstore.mapper.OrderMapper;
import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.entity.CartItem;
import com.example.oldstore.model.entity.Order;
import com.example.oldstore.model.entity.OrderItem;
import com.example.oldstore.model.entity.Product;
import com.example.oldstore.model.entity.User;
import com.example.oldstore.model.enums.OrderStatus;
import com.example.oldstore.repository.CartItemRepository;
import com.example.oldstore.repository.OrderItemRepository;
import com.example.oldstore.repository.OrderRepository;
import com.example.oldstore.repository.ProductRepository;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Transactional
	@Override
	public OrderDto createOrder(Integer userId, String recipient, String phone, String address) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("找不到使用者 ID:" + userId + "。"));
	
		List<CartItem> cartItems = cartItemRepository.findByUserWithProduct(user);
	
		if(cartItems.isEmpty()) {
			throw new IllegalStateException("購物車是空的，無法建立訂單。");
		}
		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal shippingFee = new BigDecimal("80");
		
		Order order = Order.builder()
				.user(user)
				.orderDate(LocalDateTime.now())
				.status(OrderStatus.PENDING)
				.recipient(recipient)
				.phone(phone)
				.address(address)
				.items(new ArrayList<>()) // 初始化
				.build();
		
		for(CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			Integer qty = cartItem.getQuantity();
			BigDecimal price = product.getPrice();
			
			// 檢查庫存
			if(product.getStock() < qty) {
				throw new IllegalStateException("商品庫存不足:"+ product.getName() + "。");
			}
			
			// 扣除庫存並儲存
			product.setStock(product.getStock() - qty);
			productRepository.save(product);
			
			OrderItem orderItem = OrderItem.builder()
					.order(order)
					.product(product)
					.quantity(qty)
					.price(price)
					.build();
			
			total = total.add(price.multiply(BigDecimal.valueOf(qty)));
			order.getItems().add(orderItem); 
		}
		
		order.setShippingFee(shippingFee);
		order.setTotalPrice(total.add(shippingFee));
		Order savedOrder = orderRepository.save(order);
		
		cartItemRepository.deleteByUser(user); // 清空購物車
		
		return orderMapper.toDto(savedOrder);
	}
	
	@Override
	public List<OrderDto> getOrdersByUser(Integer userId) {
	    List<Order> orders = orderRepository.findByUserIdWithItemsAndProducts(userId);

	    orders.sort(Comparator.comparing(Order::getOrderDate).reversed());
	    
	    return orders.stream()
	        .map(orderMapper::toDto)
	        .toList();
	}
	
	@Override
	public OrderDto getOrderById(Integer orderId) {
		Order order = orderRepository.findByIdWithItems(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到訂單 ID:" + orderId + "。"));
		return orderMapper.toDto(order);
	}
	
	@Override
	public List<OrderDto> getAllOrders() {
		List<Order> orders = orderRepository.findAllWithUser();
		return orders.stream()
				     .map(orderMapper::toDto)
				     .toList();
	}
	
	@Override
	@Transactional
	public void updateOrderStatus(Integer orderId, OrderStatus newStatus, boolean recordShippedTime) {
		Order order = orderRepository.findById(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("找不到訂單 ID:" + orderId + "。"));
		
		order.setStatus(newStatus);
		
		if(newStatus == OrderStatus.SHIPPED && recordShippedTime) {
			order.setShippedAt(LocalDateTime.now());
		}
		orderRepository.save(order);
	}
	
	@Override
	@Transactional
	public void simulatePayment(Integer orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到訂單 ID:" + orderId + "。"));
	
		if(order.getStatus() == OrderStatus.PENDING) {
			order.setStatus(OrderStatus.PAID);
			order.setPaidAt(LocalDateTime.now());
			orderRepository.save(order);
		}
	}
	
	@Override
	@Transactional
	public void confirmReceipt(Integer orderId, Integer userId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到訂單 ID:"+ orderId + "。"));
		
		if(!order.getUser().getUserId().equals(userId)) {
			throw new UnauthorizedActionException("無權操作。");
		}
		
		if(order.getStatus() == OrderStatus.SHIPPED) {
			order.setStatus(OrderStatus.RECEIVED);
			order.setReceivedAt(LocalDateTime.now());
			orderRepository.save(order);
		}
	}
}
