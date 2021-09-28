package com.cos.blogapp.handler.ex;

/**
 * 
 * @author Administrator
 * 1. id를 못찾았을 때 사용
 *
 */
public class MyAsyncNotFoundException extends RuntimeException{
	
	public MyAsyncNotFoundException(String msg) {
		super(msg);
	}
}
