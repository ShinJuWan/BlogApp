package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자가 만들어진다.
@Controller // 컴퍼넌트 스캔 (스프링이 실행) IoC
public class BoardController {

		// @RequiredArgsConstructor(초기화해주는 문법) and final
		// 컴퍼넌트 스캔을 통해서 IoC 컨테이너에 있는 자료를 가지고 오는 것을 DI
		// IoC에서 받아올때는 생성자 주입을 받아야한다. 
	  	// fianl이 붙은 변수는 무조건 초기화를 해줘야한다. <- 문법
	 	// DI 
		private final BoardRepository boardRepository; 
		// final + RequiredArgsConstructor를 통해서 값을 가지고 온다. 두가지가 없다면 null이 발생한다.
		private final HttpSession session;
		
		/*
		 * @RequiredArgsConstructor을 통해서 이 초기화를 대신해준다.
		 * public BoardController(BoardRepository boardRepository) { super();
		 * this.boardRepository = boardRepository; }
		 */

		//@post는 아래 3가지 all or @get body만!!
		// 1. 컨트롤러 선정, HTTp Method 선정, 3. 받을 데이터가 있는지!!(body, 쿼리스트링, 패스var)
		// 쿼리스트링 + 패스var -> db에서 where이 필요할 때!! 혹은 구체적인 요청이 필요할 때!!
		// 4. db에 접근을 해야하면 model에 접근 or else model에 접근할 필요가 없다. 
		@GetMapping("/board/{id}")
		public String detail(@PathVariable int id, Model model) {
			// select * from board where id = :id 
			// db에서 가지고온 data의 변수명 Entity
			
			// 1. orElse는 값을 찾으면 Board가 리턴, 못찾으면 (괄호안 내용 리턴)
			//Board boardEntity = boardRepository.findById(id)
			//	.orElse(new Board());
			//2 . orElseThrow
			Board boardEntity = boardRepository.findById(id)
					.orElseThrow();
			
			model.addAttribute("boardEntity", boardEntity);
			return "board/detail";
		}
		
		// @ResponseBody 파일을 리턴
		@PostMapping("/board") // http body를 가져감 -> 마임타입 전달 필수
		public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {
			
			User principal = (User) session.getAttribute("principal");
			
			// 인증체크
			if (principal == null) { // 로그인 안됨
				return Script.href("/loginForm", "잘못된 접근입니다");
			}
			
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>(); // hash map은 String key값으로 잡는다.
				for(FieldError error : bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
				}
				return Script.back(errorMap.toString());
			}
			
			boardRepository.save(dto.toEntity(principal));
			return Script.href("/", "글쓰기 성공");
		}
	
		@GetMapping("/board/saveForm")
		public String saveForm() {
			return "board/saveForm";
		}
		
		// int page = 쿼리스트링!!
		@GetMapping({"/board"})
		public String home(Model model, int page) {
					
			PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
			// 영속화된 오브젝트 (boardsEntity)
			Page<Board> boardsEntity = 
					boardRepository.findAll(pageRequest);
			// findAll을 통해서 board에 대한 것만 select 후 lazy 로딩이 걸려있으면 
			// 그 오브젝트를 사용할 때 select가 실행된다. 
			// System.out.println(boardsEntity.get(0).getUser().getUsername());
			model.addAttribute("boardsEntity", boardsEntity);
			return "board/list";
		}


}
