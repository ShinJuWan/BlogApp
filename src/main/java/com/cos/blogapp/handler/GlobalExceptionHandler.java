package com.cos.blogapp.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.Script;

// 1. Exception 핸들링, 2. @Controller의 역할까지 한다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	//@ResponsBody 데이터를 리턴하게 해준다. 
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류터졌어 : " + e.getMessage());
		return Script.href("/", e.getMessage());
	}
}
