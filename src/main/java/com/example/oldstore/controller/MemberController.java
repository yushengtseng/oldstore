package com.example.oldstore.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.oldstore.mapper.UserMapper;
import com.example.oldstore.model.dto.UserDto;
import com.example.oldstore.model.entity.User;
import com.example.oldstore.repository.UserRepository;
import com.example.oldstore.service.MailService;
import com.example.oldstore.util.HashUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Value("${site.base.url}")
	private String siteBaseUrl;

	//顯示會員個人資料
	@GetMapping("/info")
	public String showMemberInfo(HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/login";
		}
		
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
			model.addAttribute("error", "找不到會員資訊。");
			return "error";
		}
		
		UserDto userDto = userMapper.toDto(user); // Entity 轉 DTO
		model.addAttribute("user", userDto); // 傳遞 DTO 給 JSP
		return "member/info";
	}
	
	// 修改密碼驗證
	@PostMapping("/request-password-change")
	public String requestPasswordChange(@RequestParam("newPassword")String newPassword,
										@RequestParam("confirmPassword")String confirmPassword,
										HttpSession session,
										RedirectAttributes redirectAttributes) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId == null) {
			return "redirect:/login";
		}
		
		if(!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "兩次輸入的密碼不一致。");
			return "redirect:/member/info";
		}
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("找不到使用者。"));
		
		// 產生驗證碼與加密
		String code = UUID.randomUUID().toString();
		String salt = HashUtil.generateSalt();
		String passwordHash = HashUtil.hashPassword(newPassword, salt);
		
		user.setVerificationCode(code);
		user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(30));
		user.setSalt(salt);
		user.setPasswordHash(passwordHash);
		
		userRepository.save(user); // 先存驗證碼與密碼 (尚未啟用)
		
		
		// 發送驗證信
		String verifyLink = siteBaseUrl + "/member/verify-password-change?code=" + code;
		String subject = "【老式美好舊貨店】變更密碼驗證信";
		String content = String.format(
				"親愛的 %s 您好:\n\n請點選以下連結完成帳號驗證:\n%s\n\n若非本人操作請忽略此信件。"
						+ "\n此驗證連結有效時間為: 30 分鐘，逾期將失效。",
				user.getUsername(), verifyLink
			);
		
		try {
			mailService.sendMail(user.getEmail(), subject, content);
		} catch(Exception e) {
			redirectAttributes.addFlashAttribute("error", "寄送驗證信失敗，請稍後再試");
			return "redirect:/member/info";
		}
		
		redirectAttributes.addFlashAttribute("success", "已寄出驗證信，請至 Email 完成變更密碼驗證。");
		return "redirect:/member/info";
	}
	
	// 驗證密碼
	@GetMapping("/verify-password-change")
	public String verifyPasswordChange(@RequestParam("code") String code,
									   HttpServletRequest request) {
		User user = userRepository.findByVerificationCode(code);
		
		if(user == null || user.getVerificationExpiresAt() == null || 
		   user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
		   request.setAttribute("error", "驗證連結無效或已過期，請重新申請修改密碼。");
		   return "member/info";
		}
		
		// 驗證成功，密碼正式生效
		user.setVerificationCode(null);
		user.setVerificationExpiresAt(null);
		userRepository.save(user);
		
		request.setAttribute("success", "密碼修改成功，請使用新密碼重新登入。");
		return "user/login";
	}
}
