package com.KoreaIT.cgh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cgh.demo.repository.ArticleRepository;
import com.KoreaIT.cgh.demo.repository.BoardRepository;
import com.KoreaIT.cgh.demo.vo.Board;

@Service
public class BoardService {
	
    /*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	@Autowired
	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}

}