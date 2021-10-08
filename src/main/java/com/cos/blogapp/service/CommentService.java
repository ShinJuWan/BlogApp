package com.cos.blogapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 댓글삭제(int id, User principal) {
		Comment commentEntity =  commentRepository.findById(id)
			.orElseThrow(()-> new MyAsyncNotFoundException("없는 댓글 번호입니다."));
		
		if(principal.getId() != commentEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해당 게시글을 삭제할 수 없는 유저입니다.");
		}
		
		commentRepository.deleteById(id);
	}
	
	@Transactional(rollbackFor = MyNotFoundException.class)
	public void 댓글등록(int boardId, CommentSaveReqDto dto, User principal) {

		Board boardEntity = boardRepository.findById(boardId)
				.orElseThrow(() -> new MyNotFoundException("해당 게시글을 찾을 수 없습니다."));

		Comment comment = new Comment();
		comment.setContent(dto.getContent());
		comment.setUser(principal);
		comment.setBoard(boardEntity);

		commentRepository.save(comment);
	} // 트랜잭션 종료

	
}