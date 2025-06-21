package com.example.oldstore.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.dto.OrderItemDto;
import com.example.oldstore.model.entity.Order;
import com.example.oldstore.model.entity.OrderItem;

@Component
public class OrderMapper {

    public OrderDto toDto(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(this::toItemDto)
                .toList();

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .username(order.getUser().getUsername())
                .orderDate(order.getOrderDate())
                .paidAt(order.getPaidAt())
                .shippedAt(order.getShippedAt())
                .receivedAt(order.getReceivedAt())
                .shippingFee(order.getShippingFee())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .recipient(order.getRecipient())
                .phone(order.getPhone())
                .address(order.getAddress())
                .items(itemDtos)
                .build();
    }

    public OrderItemDto toItemDto(OrderItem item) {
        return OrderItemDto.builder()
                .productId(item.getProduct().getProductId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}