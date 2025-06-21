package com.example.oldstore.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, precision = 10, scale = 0)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
}
