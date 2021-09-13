package com.cos.blogapp.util;

import lombok.Getter;

// 열거형(카테고리 정할 때)
// -> 실수하지 않기 위해 사용!! (DB - 일요일 -> sun)
@Getter
public enum MyAlgorithm {
	SHA256("SHA-256"), SHA512("SHA-512");
	
	String type;
	
	private MyAlgorithm(String type) {
		this.type = type;
	}
}
