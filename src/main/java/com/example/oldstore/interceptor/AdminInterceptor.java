package com.example.oldstore.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        
        // 未登入就導向登入頁
        if (session == null || session.getAttribute("userId") == null) {
        	request.getSession(true).setAttribute("error", "請先登入，方可進入管理頁面。");
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // 若非管理者
        String role = (String) session.getAttribute("role");
        if(role == null || !role.equals("ADMIN")) {
        	session.setAttribute("error","您沒有權限進入此頁面。");
        	response.sendRedirect(request.getContextPath() + "/home");
        
        	return false;
        }	
	
		return true; // 通過驗證
	}
}