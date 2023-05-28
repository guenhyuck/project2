package com.KoreaIT.cgh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.cgh.demo.vo.Article;

/*@Mapper 어노테이션을 사용하면 MyBatis가 해당 인터페이스를 구현한 클래스를 자동으로 생성하여 사용할 수 있습니다.
이렇게 생성된 매퍼 클래스는 SQL 매핑 파일(XML 파일)이나 애노테이션을 통해 SQL 쿼리를 정의하고 실행할 수 있게 됩니다.*/
@Mapper
public interface ArticleRepository {
	

	
	
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 게시글을 작성하는 코드입니다 변수로 입력한 값이 들어갑니다
	 */

	@Insert("""
			INSERT INTO article
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			boardId = #{boardId},
			title =#{title},
			`body`= #{body}
				""")
	public void writeArticle(int memberId, int boardId, String title, String body);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 게시글 리스트를
	 * 최신글이 먼저 보이도록 내림차순으로 정렬해 값을 가져옵니다
	 */

	@Select("""
			SELECT *
			FROM article
			ORDER BY id DESC
				""")
	public List<Article> getArticles();
	
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 게시글을 조회하는 코드입니다.
	 * article 테이블을 A로 member 테이블을 B로 하여 조인문법을 사용했으며
	 * A의 값과 M의 nickname을 extra__writer로 이름지어 불러오는 쿼리문입니다
	 * A의 id 순으로 내림차순 합니다
	 * script로 감싸 동적 sql을 실행합니다
	 * if문으로 공백값을 제외합니다
	 */

	@Select("""
			<script>
			SELECT A.*,
			M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'" >
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'" >
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			ORDER BY A.id DESC
			<if test="limitFrom >= 0">
				LIMIT #{limitFrom}, #{limitTake}
			</if>
			</script>
				""")
	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			int limitFrom, int limitTake);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 게시글을 조회하는 코드입니다.
	 * 특정 게시글을 찾는 쿼리문입니다
	 */

	@Select("""
			<script>
			SELECT *
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public Article getArticle(int id);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 작성자를 조회하는 코드입니다.
	 * article 테이블을 A , member테이블을 M으로 작명하여 조인문법을 사용했으며
	 * memberid와 id가 일치하는 데이터를 찾습니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Select("""
			<script>
			SELECT A.*, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE A.id = #{id}
			</script>
			""")
	public Article getForPrintArticle(int id);
	
	
	/*
	 * 게시글을 삭제하는 메소드입니다.
	 */

	public void deleteArticle(int id);
	
	
	/*
	 * 게시글을 수정하는 메소드입니다
	 */

	public void modifyArticle(int id, String title, String body);
	
	
	/*
	 * 마지막 작성글의 값을 저장하는 변수입니다
	 */

	public int getLastInsertId();
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 게시글의 수를 조회하는 코드입니다.
	 * 값을 구하여 cnt라는 이름으로 수를 나타내며
	 * 제목,내용을 검색값으로 찾습니다.
	 * script로 감싸 동적 sql을 실행합니다
	 * if문으로 공백을 제외합니다
	 */

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'" >
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'" >
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			</script>
				""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 조회수를 증가시키는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Update("""
			<script>
				UPDATE article
				SET hitCount = hitCount + 1
				WHERE id = #{id}
			</script>
			""")

	public int increaseHitCount(int id);
	
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 조회수를 조회라는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);
	
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 좋아요를 증가시키는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReationPoint(int relId);
	
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 싫어요 증가시키는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReationPoint(int relId);
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 좋아요를 취소 시키는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReationPoint(int relId);
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 싫어요를 취소 시키는 코드입니다.
	 * script로 감싸 동적 sql을 실행합니다
	 */
	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReationPoint(int relId);

}
