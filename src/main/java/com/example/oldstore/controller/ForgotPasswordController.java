package com.example.oldstore.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.oldstore.model.entity.User;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.MailService;
import com.example.oldstore.util.HashUtil;

@Controller
public class ForgotPasswordController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
	
	// 顯示忘記密碼頁面
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm() {
		return "user/forgot_password";
	}
	
	// 寄送驗證信
	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam("email") String email,
										Model model) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			model.addAttribute("error", "查無此 Email ，請重新輸入。");
			return "user/forgot_password";
		}
		
		String resetCode = UUID.randomUUID().toString();
		user.setVerificationCode(resetCode);
		user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(30));
		userRepository.save(user);
		
		String resetLink = "http://localhost:8080/reset-password?code=" + resetCode;
		String content = String.format(
				"親愛的 %s 您好:\n\n請點選以下連結完成帳號驗證:\n%s\n\n若非本人操作請忽略此信件。"
				+ "\n此驗證連結有效時間為: 30 分鐘，逾期將失效。",
				user.getUsername(), resetLink);
		
		try {
			mailService.sendMail(email, "【老式美好舊貨店】重設密碼驗證信", content);
		} catch (Exception e) {
			model.addAttribute("error", "寄送驗證信失敗，請稍後再試。");
			return "user/forgit_password";
		}
		
		model.addAttribute("success", "已寄出驗證信，請至 Email 完成重設密碼驗證。");
		return "user/forgot_password";
	}
	
	// 顯示重設密碼表單
	@GetMapping("/reset-password")
	public String showResetForm(@RequestParam("code") String code, Model model) {
		User user = userRepository.findByVerificationCode(code);
		
		if(user == null || user.getVerificationExpiresAt() == null ||
		   user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
			
		   model.addAttribute("error", "驗證連結無效或已過期，請重新申請修改密碼。");
		   return "user/reset_password";
		}
		
		model.addAttribute("code", code);
		return "user/reset_password";
	}
	
	// 處裡重設密碼
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam("code") String code,
								@RequestParam("newPassword") String newPassword,
								@RequestParam("confirmPassword") String confirmPassword,
								Model model) {
		if(!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "兩次輸入的密碼不一致。");
			model.addAttribute("code", code);
			return "user/reset_password";
		}
		
		User user = userRepository.findByVerificationCode(code);
		if(user == null || user.getVerificationExpiresAt() == null ||
		   user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
		
		   model.addAttribute("error", "驗證連結無效或已過期，請重新申請修改密碼。");
		   return "user/reset_password";
		}
		
		// 新密碼與舊密碼，不允許一樣
		if(HashUtil.verifyPassword(newPassword, user.getSalt(), user.getPasswordHash())) {
			model.addAttribute("error", "新密碼不可與原密碼相同。");
			model.addAttribute("code", code);
			return "user/reset_password";
		}
		
		// 重設密碼
		String salt = HashUtil.generateSalt();
		String passwordHash = HashUtil.hashPassword(newPassword, salt);
		
		user.setSalt(salt);
		user.setPasswordHash(passwordHash);
		user.setVerificationCode(null);
		user.setVerificationExpiresAt(null);
		userRepository.save(user);
		
		model.addAttribute("success", "密碼修改成功，請使用新密碼重新登入。");
		return "user/login"; // 導入登入畫面
	}
}
