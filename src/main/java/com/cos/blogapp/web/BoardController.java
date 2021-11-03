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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.service.BoardService;
import com.cos.blogapp.service.CommentService;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;
import com.cos.blogapp.web.dto.CMRespDto;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 필드에 대한 생성자가 만들어진다.
@Controller // 컴퍼넌트 스캔 (스프링) IoC
public class BoardController {

	// DI
	private final BoardService boardService;
	private final CommentService commentService;
	private final HttpSession session;

	@PostMapping("/api/board/{boardId}/comment")
	public String commentSave(@PathVariable int boardId, CommentSaveReqDto dto) {
		User principal = (User) session.getAttribute("principal");
		
		commentService.댓글등록(boardId, dto, principal);
		return "redirect:/board/" + boardId;
	}

	@PutMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @RequestBody @Valid BoardSaveReqDto dto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}

		User principal = (User) session.getAttribute("principal");
		
		boardService.게시글수정(id, principal, dto);
		return new CMRespDto<>(1, "업데이트 성공", null);
	}

	@GetMapping("/api/board/{id}/updateForm")
	public String boardUpdateForm(@PathVariable int id, Model model) {
		model.addAttribute("boardEntity", boardService.게시글수정페이지이동(id));
		return "board/updateForm";
	}

	@DeleteMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		
		boardService.게시글삭제(id, principal);
	
		return new CMRespDto<String>(1, "성공", null);
	}

	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		// Board 객체에 존재하는 것 (Board(0), User(0), List<Comment>(x))
		model.addAttribute("boardEntity", boardService.게시글상세보기(id));
		return "board/detail"; // ViewResolver
	}

	@PostMapping("/api/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {

		// 공통 로직 시작 -------------------------------------------
		User principal = (User) session.getAttribute("principal");

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		// 공통 로직 끝 -------------------------------------------
		
		// 핵심 로직 시작 -------------------------------------------
		boardService.게시글등록(dto, principal);
		// 핵심 로직 끝 -------------------------------------------
		
		return Script.href("/", "글쓰기 성공");
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	// /board?page=2
	@GetMapping("/board")
	public String home(Model model, int page) {
		model.addAttribute("boardsEntity", boardService.게시글목록보기(page));
		return "board/list";
	}
}