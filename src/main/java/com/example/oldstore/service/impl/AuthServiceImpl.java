package com.example.oldstore.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.oldstore.exception.EmailSendException;
import com.example.oldstore.exception.ResourceNotFoundException;
import com.example.oldstore.model.entity.User;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.AuthService;
import com.example.oldstore.service.MailService;
import com.example.oldstore.util.HashUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
	
	@Value("${site.base.url}")
	private String siteBaseUrl;
	
	@Override
	@Transactional
	public boolean registerUser(String username, String password, String email) {
		// 檢查帳號是否存在
		if(userRepository.findByUsernameNative(username) != null) {
			return false;
		}
		
		// 檢查 email 是否存在
		if(userRepository.existsByEmail(email)) {
			return false;
		}
		
		// 產生驗證碼
		String verifyCode = UUID.randomUUID().toString();
		
		// 密碼加鹽
		String salt = HashUtil.generateSalt();
		String passwordsHash = HashUtil.hashPassword(password, salt);
		
		// 建立 User Entity
		User user = new User();
					user.setUsername(username);
					user.setPasswordHash(passwordsHash);
					user.setSalt(salt);
					user.setEmail(email);
					user.setVerificationCode(verifyCode);
					// 驗證連結有效 30 分鐘	
					user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(30));
					user.setActive(false);
					user.setRole("USER");
					
		// 儲存資料
		userRepository.save(user);
		
		// 寄出驗證信 (若失敗以 rollback 回滾拋出例外)
		String verifyLink = siteBaseUrl + "/verify?code=" + verifyCode;
		String subject = "【老式美好舊貨店】帳號驗證信";
		String content = String.format(
				"親愛的 %s 您好:\n\n請點選以下連結完成帳號驗證:\n%s\n\n若非本人操作請忽略此信件。"
				+ "\n此驗證連結有效時間為: 30 分鐘，逾期將失效。",
				username, verifyLink
			);
		
		try {
			mailService.sendMail(email, subject, content);
		}catch(Exception e){
			throw new EmailSendException("寄送驗證信失敗。", e);		
	}
		
		return true;
}
	
	@Override
	public boolean verifyUser(String code) {
		User user = userRepository.findByVerificationCode(code);
		if(user == null) {
			return false;
		}
		
		// 檢查驗證碼是否過期
		if(user.getVerificationExpiresAt() == null || user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
			return false; // 驗證碼不存在，或是已過期
		}
		
			user.setActive(true);
			user.setVerificationCode(null);
			user.setVerificationExpiresAt(null);
			userRepository.save(user);
			return true;
		
		
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsernameNative(username);
	}
}
