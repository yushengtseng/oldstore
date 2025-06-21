package com.example.oldstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.oldstore.interceptor.AdminInterceptor;
import com.example.oldstore.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// 管理員後台
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**"); // 攔截後台管理頁面
        
        // 一般使用者需要登入，才能進入的路徑
        registry.addInterceptor(new LoginInterceptor())
        		.addPathPatterns("/cart/**", "/order/**", "/member/**");
    }
    
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}



