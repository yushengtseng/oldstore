package com.example.oldstore.service;

import java.util.List;

import com.example.oldstore.model.dto.UserDto;

public interface UserService {

	List<UserDto> getAllUsers();
	UserDto getUserById(Integer userId);
	void createUser(UserDto userDto);
	void updateUser(Integer userId, UserDto userDto);
	void deleteUser(Integer userId);
	void updateUserRole(Integer userId, String newRole);
}
