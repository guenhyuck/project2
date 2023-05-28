package com.KoreaIT.cgh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cgh.demo.repository.ReactionPointRepository;
import com.KoreaIT.cgh.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
    /*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;
	
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 하고 오렴");
		}
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);
		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}
	
	
	
	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		if (affectedRow != 1) {
			return ResultData.from("F-2", "좋아요 실패");
		}
		//게시글 좋아요
		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReationPoint(relId);
			break;
		}
		//댓글 좋아요
		switch (relTypeCode) {
		case "reply":
			replyService.increaseGoodReationPoint(relId);
			break;
		}
		return ResultData.from("S-1", "좋아요 처리 됨");
	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "싫어요 실패");
		}
		//게시글 좋아요

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReationPoint(relId);
			break;
		}
		//댓글 좋아요
		switch (relTypeCode) {
		case "reply":
			replyService.increaseBadReationPoint(relId);
			break;
		}
		return ResultData.from("S-1", "싫어요 처리 됨");
	}

	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);
		//게시글 좋아요
		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReationPoint(relId);
			break;
		}
		//댓글 좋아요
		switch (relTypeCode) {
		case "reply":
			replyService.decreaseGoodReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 취소 처리 됨");
	}

	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);
		//게시글 좋아요
		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReationPoint(relId);
			break;
		}
		//댓글 좋아요
		switch (relTypeCode) {
		case "reply":
			replyService.decreaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 취소 처리 됨");
	}
	

	

}