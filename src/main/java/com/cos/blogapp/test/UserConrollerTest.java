package com.cos.blogapp.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserConrollerTest {
	
	@GetMapping("/test/join")
	public @ResponseBody String testJoin() {
		return "test/join";
	}
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin() {
		return "<script>alert('hello');</script>";
	}
	
	// 쿼리스티링 = 주소?name=홍길동 
	// /test/data/1
	@GetMapping("/test/data/{num}")
	public @ResponseBody String testData(@PathVariable int num) {
		
		if (num == 1 ) { // 정상
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("location.href='/'; ");
			sb.append("</script>");
			
			return sb.toString();
		} else {// 오류
			return "오류가 발생했습니다.";
		}

	}
}
