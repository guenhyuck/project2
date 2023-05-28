package com.KoreaIT.cgh.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.KoreaIT.cgh.demo.repository.ArticleRepository;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Article;
import com.KoreaIT.cgh.demo.vo.ResultData;

@Service
public class ArticleService {
	
    /*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	

	@Autowired
	private ArticleRepository articleRepository;
	
	
	/*
	 * 생성자를 생성해 articleRepository 객체를 articleRetository 변수에 저장합니다
	 */
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	
	/*
	 * writeArticle: 회원 ID, 게시판 ID, 제목, 본문을 전달받아 게시물을 작성합니다.
	 * articleRepository.writeArticle 메서드를 호출하여 데이터베이스에 게시물을 저장하고, 마지막에 생성된 게시물의 ID를
	 * 반환합니다.
	 */

	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {

		articleRepository.writeArticle(memberId, boardId, title, body);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id), "id", id);

	}
	/*
	 * deleteArticle: 주어진 ID에 해당하는 게시물을 삭제합니다. articleRepository.deleteArticle 메서드를
	 * 호출하여 데이터베이스에서 게시물을 삭제합니다.
	 */

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}
	
	/*
	 * modifyArticle: 주어진 ID에 해당하는 게시물의 제목과 본문을 수정합니다.
	 * articleRepository.modifyArticle 메서드를 호출하여 데이터베이스에서 게시물을 수정한 후, 수정된 게시물을
	 * 반환합니다.
	 */

	public ResultData modifyArticle(int id, String title, String body) {

		articleRepository.modifyArticle(id, title, body);

		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), "article", article);
	}
	
	/*
	 * articles: 모든 게시물의 목록을 반환합니다. articleRepository. getArticles 메서드를 호출하여
	 * 데이터베이스에서 모든 게시물을 가져옵니다.
	 */

	public List<Article> articles() {
		return articleRepository.getArticles();
	}

	/*
	 * getArticle: 주어진 ID에 해당하는 게시물을 반환합니다. articleRepository.getArticle 메서드를 호출하여
	 * 데이터베이스에서 해당 게시물을 가져옵니다.
	 */
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	/*
	 * getForPrintArticle: 특정 사용자의 권한을 고려하여 주어진 ID에 해당하는 게시물을 반환합니다.
	 * articleRepository.getForPrintArticle 메서드를 호출하여 데이터베이스에서 해당 게시물을 가져온 후,
	 * controlForPrintData 메서드를 사용하여 사용자의 권한을 확인합니다.
	 */
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(actorId, article);

		return article;
	}
	/*
	 * controlForPrintData 메서드: 사용자의 권한을 확인하고, 해당 게시물의 수정 및 삭제 가능 여부를 설정합니다. 먼저,
	 * 게시물이 존재하지 않는 경우에는 아무 작업도 수행하지 않고 종료합니다. 그렇지 않은 경우에는 actorCanDelete 메서드를 호출하여
	 * 사용자가 해당 게시물을 삭제할 수 있는지 여부를 확인하고, actorCanModify 메서드를 호출하여 사용자가 해당 게시물을 수정할 수
	 * 있는지 여부를 확인합니다. 확인 결과를 Article 객체의 actorCanDelete 및 actorCanModify 속성에 설정합니다.
	 */

	private void controlForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setActorCanModify(actorCanModifyRd.isSuccess());
	}
	
	
	
   
	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	private ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}
	
	/*
	 * getForPrintArticles 메서드: 특정 게시판에서 페이징 처리된 게시물 목록을 가져옵니다. 페이지 번호와 페이지당 아이템 수를
	 * 기반으로 데이터베이스에서 해당 범위의 게시물을 가져옵니다.
	 */

	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchKeywordTypeCode,
			String searchKeyword) {

		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		return articleRepository.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom,
				limitTake);
	}

	/*
	 * getArticlesCount 메서드: 특정 게시판에서의 게시물 수를 반환합니다. 데이터베이스에서 해당 게시판의 게시물 수를 가져와
	 * 반환합니다.
	 */
	
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	/*
	 * increaseHitCount 메서드: 주어진 ID에 해당하는 게시물의 조회수를 증가시킵니다.
	 * articleRepository.increaseHitCount 메서드를 호출하여 데이터베이스에서 해당 게시물의 조회수를 증가시킵니다.
	 */
	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없음", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}
	/*
	 * getArticleHitCount 메서드: 주어진 ID에 해당하는 게시물의 조회수를 반환합니다. 데이터베이스에서 해당 게시물의 조회수를
	 * 가져와 반환합니다.
	 */

	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}
	
	/*
	 * increaseGoodReationPoint 및 increaseBadReationPoint 메서드: 주어진 ID에 해당하는 게시물의 좋아요
	 * 및 싫어요 수를 증가시킵니다. articleRepository.increaseGoodReationPoint 및
	 * articleRepository.increaseBadReationPoint 메서드를 호출하여 데이터베이스에서 해당 게시물의 좋아요 및
	 * 싫어요 수를 증가시킵니다
	 */
	public ResultData increaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	public ResultData increaseBadReationPoint(int relId) {

		int affectedRow = articleRepository.increaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}
	
	
	
	
	
	/*
	 * decreaseGoodReationPoint 및 decreaseBadReationPoint 메서드: 주어진 ID에 해당하는 게시물의 좋아요
	 * 및 싫어요 수를 감소시킵니다. articleRepository.decreaseGoodReationPoint 및
	 * articleRepository.decreaseBadReationPoint 메서드를 호출하여 데이터베이스에서 해당 게시물의 좋아요 및
	 * 싫어요 수를 감소시킵니다.
	 */

	public ResultData decreaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	public ResultData decreaseBadReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);

	}

}