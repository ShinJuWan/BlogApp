package com.cos.blogapp.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

//CRUD를 가지고있다(DAO). controller는 Repository를 통해서 db에 연결된다. 
//JpaRepository를 상속받았기 때문에 @controller가 없어도 컴포넌트스캔이 자동으로 가능하다. 
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}