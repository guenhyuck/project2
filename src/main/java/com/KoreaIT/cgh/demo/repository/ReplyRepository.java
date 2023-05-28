package com.KoreaIT.cgh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.cgh.demo.vo.Reply;


/*@Mapper 어노테이션을 사용하면 MyBatis가 해당 인터페이스를 구현한 클래스를 자동으로 생성하여 사용할 수 있습니다.
이렇게 생성된 매퍼 클래스는 SQL 매핑 파일(XML 파일)이나 애노테이션을 통해 SQL 쿼리를 정의하고 실행할 수 있게 됩니다.*/
@Mapper
public interface ReplyRepository {
	
	
	/* DB에 insert 쿼리문을 이용하여 reply 테이블에 값을 넣는 쿼리문입니다
	 * script를 이용하여 동적sql로
	 * 각각 입력한 값을 insert합니다
	 */
	
	
	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId =#{relId},
				`body`= #{body}
			</script>
			""")

	void writeReply(int actorId, String relTypeCode, int relId, String body);
	
	/* DB에 select 문을 이용하여 마지막 insert된 id값을 조회하는 쿼리입니다
	 * script를 이용하여 동적sql로
	 * 입력한 값을 select합니다
	 */
	
	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	int getLastInsertId();
	
	/* DB에 select 문을 이용하여 reply테이블을
	 * id순으로 오름차순하여 컬럼을 모두 보여주는 쿼리입니다
	 */
	
	@Select("""
			SELECT *
			FROM reply
			ORDER BY id DESC
				""")
	public List<Reply> getReplys();
	
	/* DB에 select 문을 이용하여 reply테이블을
	 * 입련된 id값으로 조회합니다
	 */
	


	@Select("""
			<script>
			SELECT *
			FROM reply
			WHERE id = #{id}
			</script>
			""")
	public Reply getReply(int id);
	
	/*
	 * select 문을 이용하여 reply테이블을 R로 member테이블을 M으로 축약하여 R.memberId와 M.id를 inner 조인한 뒤 R.의
	 * 모든값과 M.nickname을 extra_writer로 명명하고 댓글쓴 member를 찾는 쿼리문입니다
	 */

	@Select("""
			<script>
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.id = #{id}
			</script>
			""")
	public Reply getForPrintReplise(int memberId,int id);
	
	/*
	 * select 문을 이용하여 reply테이블을 R로 member테이블을 M으로 축약하여 R.memberId와 M.id를 left 조인한 뒤 R.의
	 * 모든값과 M.nickname을 extra_writer로 명명하고 댓글쓴 member를 찾는 쿼리문입니다
	 * R.relTypeCode와 R.relId 입력한 값을 바탕으로 찾으며
	 * R.id값으로 오름차순 합니다
	 */
	
	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			LEFT JOIN `member` AS M
			ON R.memberId = M.id
			WHERE R.relTypeCode = #{relTypeCode}
			AND R.relId = #{relId}
			ORDER BY R.id ASC
		""")
     List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);
	
		/*
		 * 댓글을 삭제하는 쿼리문입니다
		 */
	@Delete("""
			<script>
		DELETE FROM
		reply
		WHERE id = #{id}
		</script>
			""")

			
	
	public void deleteReply(int id);
	
	/*
	 * 댓글을 수정하는 쿼리문입니다
	 */
	
	@Update("""
		<script>
		UPDATE reply
		<set>
		<if test="body != null and body != ''">`body` = #{body},</if>
		updateDate= NOW()
		</set>
		WHERE id = #{id}
		</script>
			""")

	public void modifyReply(int id, String body);


	/*
	 * reply 테이블에 좋아요를 1 증가시키는 쿼리문입니다
	 * 입력한 id값을 relId값입니다
	 */
	
	@Update("""
			<script>
				UPDATE reply
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReationPoint(int relId);
	
	
	/*
	 * reply 테이블에 싫어요를 1 증가시키는 쿼리문입니다
	 * 입력한 id값을 relId값입니다
	 */

	@Update("""
			<script>
				UPDATE reply
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReationPoint(int relId);
	
	/*
	 * reply 테이블에 좋아요를 -1 증가시키는 쿼리문입니다
	 * 결과적으로 좋아요 취소가 됍니다
	 * 입력한 id값을 relId값입니다
	 */
	

	@Update("""
			<script>
				UPDATE reply
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReationPoint(int relId);
	
	/*
	 * reply 테이블에 싫어요를 -1 증가시키는 쿼리문입니다
	 *  결과적으로 싫어요 취소가 됍니다
	 * 입력한 id값을 relId값입니다
	 */

	@Update("""
			<script>
				UPDATE reply
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReationPoint(int relId);

	

}

