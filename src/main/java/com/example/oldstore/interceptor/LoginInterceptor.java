package com.example.oldstore.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("userId") == null) {
			session.setAttribute("errorMsg", "請先登入，以繼續操作。");
			response.sendRedirect("/login");
			return false;
		}
		
		return true;
	}
}
