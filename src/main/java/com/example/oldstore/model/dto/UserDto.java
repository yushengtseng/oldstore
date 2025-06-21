package com.example.oldstore.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
	
	private Integer userId;
	
	@NotBlank(message = "{userDto.username.notBlank}")
    @Size(max = 50, message = "{userDto.username.size}")
    private String username;

    @NotBlank(message = "{userDto.email.notBlank}")
    @Email(message = "{userDto.email.email}")
    private String email;

    @NotNull(message = "{userDto.active.notNull}")
    private Boolean active;
    
    @NotBlank(message = "{userDto.role.notBlank}")
    private String role;
    
    private LocalDateTime createdAt;
    public String getFormattedCreatedAt() {
    	if(createdAt == null) return null;
    	return this.createdAt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}