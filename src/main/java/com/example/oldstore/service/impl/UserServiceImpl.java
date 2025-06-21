package com.example.oldstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.oldstore.exception.ResourceNotFoundException;
import com.example.oldstore.mapper.UserMapper;
import com.example.oldstore.model.dto.UserDto;
import com.example.oldstore.model.entity.User;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.UserService;
import com.example.oldstore.util.HashUtil;

@Service
public class UserServiceImpl implements UserService{
	
	private static final String MASTER_ADMIN = "MasterAdmin";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream()
				.map(userMapper::toDto)
				.toList();
	}
	
	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到會員 ID:" + userId + "。"));
		return userMapper.toDto(user);
	}
	
	@Override
	public void createUser(UserDto userDto) {
		
		// 檢查 email 是否重複
		if(userRepository.existsByEmail(userDto.getEmail())) {
			throw new IllegalArgumentException("此 Email 已存在，請使用其他 Email。");
		}
		
		User user = userMapper.toEntity(userDto);
		
		// 預設密碼
		String rawPassword = "abc123";
		String salt = HashUtil.generateSalt();
		String passwordHash = HashUtil.hashPassword(rawPassword, salt);
		
		user.setPasswordHash(passwordHash);
		user.setSalt(salt);
		user.setActive(true);
		userRepository.save(user);
	}
	
	@Override
	public void updateUser(Integer userId, UserDto userDto) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到會員 ID:" + userId + "。"));
		
		if(MASTER_ADMIN.equals(user.getUsername())) {
			throw new IllegalArgumentException("不可修改 MasterAdmin 帳號。");
		}
		
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setActive(userDto.getActive());
		userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到會員 ID:" + userId + "。"));
		
		if(MASTER_ADMIN.equals(user.getUsername())) {
			throw new IllegalArgumentException("不可刪除 MasterAdmin 帳號。");
		}
		
		userRepository.delete(user);
	}
	
	@Override
	public void updateUserRole(Integer userId, String newRole) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到會員 ID:" + userId + "。"));
		
		if(MASTER_ADMIN.equals(user.getUsername())) {
			throw new IllegalArgumentException("不可修改 MasterAdmin 帳號權限。");
		}
		user.setRole(newRole);
		userRepository.save(user);
	}
}
