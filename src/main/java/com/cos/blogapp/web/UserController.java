package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자가 만들어진다.
@Controller // 컴퍼넌트 스캔 (스프링) ioc
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession session;	// tomcat이 만들어서 ioc에 제공해준다.
	
	@GetMapping("/user/{id}")
	public String userInfo(@PathVariable int id) {
		// 정석은 userRepository.findById(id)를 db에서 가져와야함.
		// 편법은 세션값을 가져올 수도 있다.
		
		return "user/updateForm";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); // session 무효화. session에 있는 값을 비운다. 
		// return "board/list"; // 게시글 목록 화면에 데이터가 있을까? 
		return "redirect:/";
	}
	
	// https://localhost:8080/login -> login.jsp
	// views/user/login.jsp
	// prefix: /WEB-INF/views/	( 찾으려는 내용 )	suffix: .jsp  
	// ViewResolver
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}
	
	@PostMapping("/login")
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {

		// System.out.println("에러사이즈:" + bindingResult.getFieldErrors().size()); // Array list는 숫자로 카운트!
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>(); // hash map은 String key값으로 잡는다.
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		// 로그인 실패
		User userEntity = userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
		
		if(userEntity == null ) { // username, password 잘못기입
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		} else {
			session.setAttribute("principal", userEntity); //principal 접근, 인증 주체 = 인증된 오브젝트
			return Script.href("/", "로그인 성공");
		}

	}
	
	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) { // username=love&password=1234&email=love@nate.com
		
		// 1. 유효성 검사 실패 -> 자바스크립트 응답(alert창, history.back())
		// 2. 정상 -> 로그인 페이지
		
		// System.out.println("에러사이즈:" + bindingResult.getFieldErrors().size()); // Array list는 숫자로 카운트!
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>(); // hash map은 String key값으로 잡는다.
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드:" + error.getField());
				System.out.println("메세지:" + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		String encPassword =  SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		
		dto.setPassword(encPassword);
		userRepository.save(dto.toEntity());
		return Script.href("/loginForm"); // 리다이렉션 (300)
	}
	
}
