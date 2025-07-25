package com.example.oldstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.entity.Order;

@Configuration // Springboot 啟動完成前會先執行此配置
public class ModelMapperConfig {
	
	// Springboot 會自動建立此物件並管理
	// 其他程式可以透過 @Autowired 來取得該實體物件
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}