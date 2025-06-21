package com.example.oldstore.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
	
	PENDING("待結帳"),     
	PAID("已結帳，訂單處理中"),   
	SHIPPED("已出貨"),    
	RECEIVED("已收貨"),
	COMPLETED("已完成"),
	CANCELED("已取消");     
	
	private final String displayName;
	
	OrderStatus(String displayName) {
		this.displayName = displayName;
	}
}
