package com.KoreaIT.cgh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cgh.demo.service.ReactionPointService;
import com.KoreaIT.cgh.demo.vo.ResultData;
import com.KoreaIT.cgh.demo.vo.Rq;


//좋아요, 싫어요에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언

/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
public class UsrReactionPointController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다. 
	 * Rq rq: Rq 객체를 주입받습니다.
	 * ReactionPointService reactionPointService: ReactionPointService 인터페이스를 구현한
	 * 서비스 객체입니다.
	 */
	
	
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	/* relTypeCode, relId,replaceUri를 파라미터로 받습니다. */
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);
		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack("F-1", "중복체크 불가");
		}
		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	/* relTypeCode, relId,replaceUri를 파라미터로 받습니다. */
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);
		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack("F-1", "중복체크 불가");
		}
		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	/* relTypeCode, relId,replaceUri를 파라미터로 받습니다. */
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);
		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}
		ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 취소 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelBadReaction")
  
	@ResponseBody
	/* relTypeCode, relId,replaceUri를 파라미터로 받습니다. */
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);
		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}
		ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "싫어요 취소 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

}