package com.cos.blogapp.test;

// java 1.8에서 생성. 람다식.
// -> stack을 넘길 수 있다. 
// 1. 함수를 넘기는게 목적 (익명 함수, 람다 함수)
// 2. 인터페이스에 함수가 무조건 하나여야 함. 
// 3. 사용하면 코드가 간결해지고, 타입을 몰라도 됨. 
interface MySupplier {
	void get();
}

public class LamdaTest {
	
	static void start(MySupplier s) {
		s.get();
	}
	
	public static void main(String[] args) {

		start(() -> {System.out.println("get함수 호출됨");});
		// = start(() -> System.out.println("get함수 호출됨"));
	}
}
