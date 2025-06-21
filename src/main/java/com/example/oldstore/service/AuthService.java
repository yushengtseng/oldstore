package com.example.oldstore.service;

import com.example.oldstore.model.entity.User;

public interface AuthService {
	
	boolean registerUser(String username, String password, String email);
	boolean verifyUser(String code); // 驗證密碼用
	User getUserByUsername(String username);
}
