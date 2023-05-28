package com.KoreaIT.cgh.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cgh.demo.service.ArticleService;
import com.KoreaIT.cgh.demo.service.BoardService;
import com.KoreaIT.cgh.demo.service.ReactionPointService;
import com.KoreaIT.cgh.demo.service.ReplyService;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Article;
import com.KoreaIT.cgh.demo.vo.Board;
import com.KoreaIT.cgh.demo.vo.Reply;
import com.KoreaIT.cgh.demo.vo.ResultData;
import com.KoreaIT.cgh.demo.vo.Rq;


//게시글에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언


/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
public class UsrArticleController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다. ReplyService replyService:
	 * ReplyService 인터페이스를 구현한 서비스 객체입니다.
	 * private BoardService : BoardService 인터페이스를 구현한 서비스 객체입니
	 * ArticleService articleService:ArticleService 인터페이스를 구현한 서비스 객체입니다.
	 * Rq rq: Rq 객체를 주입받습니다.
	 * ReactionPointService reactionPointService: ReactionPointService 인터페이스를 구현한
	 * 서비스 객체입니다.
	 */
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;


  
	
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	
	@RequestMapping("/usr/article/list")
	/* model , boardId,searchKeywordTypeCode,searchKeyword
	 * page를 파라미터로 받습니다. */
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		
		
		/*
		 * boardService에서 boarId값을 가져와서 board 변수에 저장한 뒤 값을 if문으로 따져봅니다 
		 * null이라면 메시지를 출력합니다
		 */
		Board board = boardService.getBoardById(boardId);
		if (board == null) {
			return rq.jsHistoryBackOnView("없는 게시판이야");
		}
		
/*		페이징을 하기위한 변수 선언입니다
		articleService에서 변수들을 가져와 articlesCount에 저장합니다 게시글 총 수를 보여줍니다
		itemsInAPage = 10을 선언해 한 페이지에 10개씩 보여줍니다
		pagesCount 변수:총 페이지 수를 나타내는 변수입니다.(int) Math.ceil(articlesCount / (double) itemsInAPage)를 통해 
		총 게시글 수를 페이지당 게시글 수로 나누고 올림하여 총 페이지 수를 계산합니다.*/
		
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
		int itemsInAPage = 10;
		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);
		
		
		/*
		 * 이렇게 하면 articlesCount 변수에는 총 게시글 개수가 저장되고, pagesCount 변수에는 총 페이지 수가 저장됩니다.
		 * articles 리스트에는 해당 페이지에 표시될 게시글 목록이 저장됩니다.
		 */
		
		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword);

//		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
//
//		int repliesCount = replies.size();
//
//		model.addAttribute("repliesCount", repliesCount);

		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);

  
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);
		
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/article/list로 반환합니다
		 */
		
		return "usr/article/list";
	}
	@RequestMapping("/usr/article/modify")
	/* model , id를 파라미터로 받습니다. */
	public String showModify(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}
		model.addAttribute("article", article);
		
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/article/modify 로 반환합니다
		 */
		return "usr/article/modify";
	}
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	/* id , title, body를 파라미터로 받습니다. */
	public String doModify(int id, String title, String body) {
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
	}
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	/*  id를 파라미터로 받습니다. */
	public String doDelete(int id) {
		
		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		
		articleService.deleteArticle(id);
		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list?boardId=1");
	}
	
	@RequestMapping("/usr/article/write")
	/* title ,body,를 파라미터로 받습니다. */
	public String showWrite(String title, String body) {
		
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/article/write 로 반환합니다
		 */
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	/* boardId,title,body,replaceUri를 파라미터로 받습니다. */
	public String doWrite(int boardId, String title, String body, String replaceUri) {
		
		
		/* 파라미터 값을 입력받지 못했다면 해당 코드와 메세지를 보여줍니다 */
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
		int id = (int) writeArticleRd.getData1();
		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		
		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다", id), replaceUri);
	}
	
	@RequestMapping("/usr/article/detail")
	/* model , id를 파라미터로 받습니다. */
	public String showDetail(Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"article", id);

		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);

		int repliesCount = replies.size();

		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("replies", replies);
		model.addAttribute("article", article);
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());

		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();
			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/article/detail 로 반환합니다
		 */
		return "usr/article/detail";
	}
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	/* id를 파라미터로 받습니다. */
	public ResultData doIncreaseHitCountRd(int id) {
		
		ResultData increaseHitCountRd = articleService.increaseHitCount(id);
		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		
		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));
		rd.setData2("id", id);
		return rd;
	}
}