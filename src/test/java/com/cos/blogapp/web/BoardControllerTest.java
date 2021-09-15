package com.cos.blogapp.web;

import com.cos.blogapp.domain.board.Board;


public class BoardControllerTest {
	public void 익센션테스트() {
		try {
			Board b = null;
			System.out.println(b.getContent());
		} catch (Exception e) {
			System.out.println("오류가 났어요");
			System.out.println(e.getMessage());
		}
	}
}
