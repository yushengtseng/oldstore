package com.example.oldstore.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {

	// 產生隨機 salt
	public static String generateSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	// 雜湊加密 (SHA-256 + salt)
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String saltedPassword = password + salt;
			byte[] hashedBytes = md.digest(saltedPassword.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("無法使用 SHA-256 加密", e);
		}
	}
	
	// 驗證密碼是否正確
	public static boolean verifyPassword(String rawPassword, String salt, String hashedPassword) {
		String hashToCompare = hashPassword(rawPassword, salt);
		return hashedPassword.equals(hashToCompare);
	}
}
