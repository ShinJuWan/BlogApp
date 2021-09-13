package com.cos.blogapp.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 50)
	private String title;
	@Lob	// maria db에서 longtext로 설정해주는 annotation -> 4GB
	private String content; 
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.LAZY) 
	// lazy : 지연로딩, eager : 사용하기 전에 미리 땡겨온다.
	// 어차피 땡겨올게 1건 밖에 없다. 부하가 과하지 않기 때문에 기본 전략이 eager이다. 
	// 1건이 아니라 여러건을 땡겨와야하면 다 lazy다. 
	private User user;
}
