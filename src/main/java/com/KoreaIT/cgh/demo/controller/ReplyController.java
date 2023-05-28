package com.KoreaIT.cgh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cgh.demo.service.ArticleService;
import com.KoreaIT.cgh.demo.service.ReactionPointService;
import com.KoreaIT.cgh.demo.service.ReplyService;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Article;
import com.KoreaIT.cgh.demo.vo.Reply;
import com.KoreaIT.cgh.demo.vo.ResultData;
import com.KoreaIT.cgh.demo.vo.Rq;


//댓글에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언

/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
@RequestMapping()
public class ReplyController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다. ReplyService replyService:
	 * ReplyService 인터페이스를 구현한 서비스 객체입니다. ArticleService articleService:
	 * ArticleService 인터페이스를 구현한 서비스 객체입니다. Rq rq: Rq 객체를 주입받습니다.
	 * ReactionPointService reactionPointService: ReactionPointService 인터페이스를 구현한
	 * 서비스 객체입니다.
	 */
	
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;

	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
 
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	/*
	 * doWrite(): 댓글 작성을 처리하는 메서드입니다. 필요한 파라미터들을 받고, 작성 결과를 문자열 형태로 반환합니다.
	 * 
	 * showModify(): 댓글 수정 화면을 보여주는 메서드입니다. 필요한 파라미터를 받고, 댓글과 관련된 데이터를 모델에 추가하여 뷰를
	 * 반환합니다.
	 * 
	 * doModify(): 댓글 수정을 처리하는 메서드입니다. 필요한 파라미터를 받고, 수정 결과를 문자열 형태로 반환합니다.
	 * 
	 * doDelete(): 댓글 삭제를 처리하는 메서드입니다. 필요한 파라미터를 받고, 삭제 결과를 문자열 형태로 반환합니다.
	 */
	
	
	
	 
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	/* relTypeCode, relId, body, replaceUri를 파라미터로 받습니다. */
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {
		
		
		/* 파라미터 값을 입력받지 못했다면 해당 코드와 메세지를 보여줍니다 */

		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("F-1", "relTypeCode 을(를) 입력해주세요");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("F-2", "relId 을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-3", "body 을(를) 입력해주세요");
		}

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = (int) writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}
	

		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reply/modify")
	/* model , id를 파라미터로 받습니다. */
	public String showModify(Model model, int id) {
		Reply reply = replyService.getForPrintReplise(rq.getLoginedMemberId(), id);
		
		if (reply == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다!", id));
		}
		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}
		
		Article article = articleService.getArticle(reply.getRelId());
		
		model.addAttribute("reply", reply);
		model.addAttribute("article", article);
		return "usr/reply/modify";
	}

	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	/* id , body를 파라미터로 받습니다. */
	public String doModify(int id, String body) {
		Reply reply = replyService.getReply(id);
		if (reply == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다@", id));
		}
		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}
		replyService.modifyReply(id, body);
		return rq.jsReplace(Ut.f("%d번 댓글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", reply.getRelId()));
	}

	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	/* id를 파라미터로 받습니다. */
	public String doDelete(int id) {
		Reply reply = replyService.getReply(id);
		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}
		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}
		replyService.deleteReply(id);
		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace(Ut.f("%d번 댓글을 삭제 했습니다", id), Ut.f("../article/detail?id=%d", reply.getRelId()));

	}

}
