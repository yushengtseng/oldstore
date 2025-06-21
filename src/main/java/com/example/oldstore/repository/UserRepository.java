package com.example.oldstore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oldstore.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// 用於登入查詢:用 native SQL 查詢完整欄位
	@Query(value = "SELECT * FROM users WHERE username = :username",
					nativeQuery = true)
	User findByUsernameNative(String username);
	
	// 用戶驗證
	User findByVerificationCode(String code);
	
	// 檢查 email
	boolean existsByEmail(String email);
	
	User findByEmail(String email);
}
