package com.example.oldstore.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@Column(nullable = false, length = 255)
	private String passwordHash;
	
	@Column(nullable = false, length = 255)
	private String salt;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	private Boolean active = false;
	
	@Column(length = 100)
	private String verificationCode;
	
	@Column(name = "verification_expires_at")
	private LocalDateTime verificationExpiresAt;
	
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(length = 20)
	private String role = "USER";
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
}
