package com.cos.blogapp.handler.ex;

/**
 * 
 * @author Administrator
 * 1. id를 못찾았을 때 사용
 *
 */
public class MyAsyncNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MyAsyncNotFoundException(String msg) {
		super(msg);
	}
}
