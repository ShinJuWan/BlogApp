package com.cos.blogapp.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;

public class SessionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle 실행됨");
		
		HttpSession session = request.getSession();
		
		User principal = (User) session.getAttribute("principal");
		if(principal == null) {
			response.sendRedirect("/loginForm");
		}
		
		return true;
	}
}
