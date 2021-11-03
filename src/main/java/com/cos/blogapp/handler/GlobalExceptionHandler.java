package com.cos.blogapp.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.CMRespDto;

// 1. Exception 핸들링, 2. @Controller의 역할까지 한다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 일반요청
	//@ResponsBody 데이터를 리턴하게 해준다. 
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류터졌어 : " + e.getMessage());
		return Script.href("/", e.getMessage());
	}
	
	
	
	// fetch 요청 (데이터를 응답받아야 할 때)
	@ExceptionHandler(value = MyAsyncNotFoundException.class)
	public CMRespDto<String> error2(MyAsyncNotFoundException e) {
		System.out.println("오류 터졌어 : " + e.getMessage());
		return new CMRespDto<String>(-1, e.getMessage(), null);
	}
}
