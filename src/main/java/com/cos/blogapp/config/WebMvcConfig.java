package com.cos.blogapp.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.blogapp.handler.SessionInterceptor;

// 서버가 최초 실행될 때, ioc컨테이너에서 WebMvcConfigurer 타입을 찾아내서 실행한다. 누가? 디스패쳐 서블릿이!!
@Configuration // 설정파일에 붙여야하는 어노테이션
public class WebMvcConfig implements WebMvcConfigurer{
	public WebMvcConfig() {
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor())
		.addPathPatterns("/api/**");
	}
	

}
