package com.example.oldstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.oldstore.model.entity.User;
import com.example.oldstore.service.AuthService;
import com.example.oldstore.util.AuthCodeGenerator;
import com.example.oldstore.util.HashUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private AuthService authService;
	
	// 用戶註冊系統頁面
	@GetMapping("/register")
	public String showRegisterPage() {
		return "user/register";
	}
	
	// 處理用戶註冊送出
	@PostMapping("/register")
	public String handleRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		boolean success = authService.registerUser(username, password, email);
		
		if(success) {
			request.setAttribute("success", "已寄出驗證信，請至 Email 完成帳號驗證。");
		}else {
			request.setAttribute("error","帳號或 Email 已存在，請使用其他資訊。");
		}
		return "user/register";
	}
	
	// 驗證用戶
	@GetMapping("/verify")
	public String verifyUser(@RequestParam("code")String code, HttpServletRequest request) {
		boolean verified = authService.verifyUser(code);
		if(verified) {
			request.setAttribute("success","驗證成功，請登入。");
			return "user/login"; 
		}else {
			request.setAttribute("error","驗證失敗。");
			return "user/verify_fail";
		}
	}
	
	
	
	
	
	
	
	
	
	// 用戶登入系統頁面
	@GetMapping("/login")
	public String showLoginPage(HttpServletRequest request) {
		
		// 預設為空
		String rememberedUsername = "";
		
		// 從 Cookie 中讀取
		if(request.getCookies() != null) {
			for(var cookie : request.getCookies()) {
				if("rememberedUsername".equals(cookie.getName())) {
					rememberedUsername = cookie.getValue();
					break;
				}
			}
		}
		request.setAttribute("rememberedUsername", rememberedUsername);
		return "user/login";
	}
	
	@PostMapping("/login")
	public String handleLogin(HttpServletRequest request,
							  HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String inputCode = request.getParameter("authcode");
		String rememberMe = request.getParameter("rememberMe"); // 記住我
		HttpSession session = request.getSession();
		
		// 驗證碼比對
		String sessionCode = (String) session.getAttribute("authCode");
		if(sessionCode == null || !sessionCode.equalsIgnoreCase(inputCode)) {
			request.setAttribute("error", "驗證碼錯誤。");
			return "user/login";
		}
		
		// 清除一次性驗證碼
		session.removeAttribute("authCode");
		
		User user = authService.getUserByUsername(username);
		// 驗證使用者是否存在
		if(user == null) {
			request.setAttribute("error","帳號不存在。");
			return "user/login";
		}
		
		// 驗證是否已啟用
		if(!user.getActive()) {
			request.setAttribute("error","帳號尚未完成驗證。");
			return "user/login";
		}
		
		// 驗證密碼是否正確
		boolean match = HashUtil.verifyPassword(password, user.getSalt(), user.getPasswordHash());
		if(!match) {
			request.setAttribute("error","密碼錯誤。");
			return "user/login";
		}
		
		// 寫入 Cookie 或清除
		if("on".equals(rememberMe)) {
			jakarta.servlet.http.Cookie cookie = 
				new jakarta.servlet.http.Cookie("rememberedUsername", username);
			cookie.setMaxAge(7 * 24 * 60 * 60); // 7天
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			jakarta.servlet.http.Cookie cookie = 
				new jakarta.servlet.http.Cookie("rememberedUsername", null);
			cookie.setMaxAge(0); // 刪除 cookie
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		// 成功登入，將使用者資訊寫入 Session
		session.setAttribute("userId", user.getUserId());
		session.setAttribute("username",user.getUsername());
		session.setAttribute("role",user.getRole());
		
		return "redirect:/home";
		
	}
	
	// 用戶登出系統頁面
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		// 清除 session
		request.getSession().invalidate();
		
		return "redirect:/home";
	}
	
	
	
	
	
	
	
	
	
	
	// 登入系統驗證機制
	@GetMapping("/authcode")
	public void getAuthCode(HttpServletResponse response, HttpSession session)throws IOException {
		AuthCodeGenerator.generate(response, session);
	}
}
