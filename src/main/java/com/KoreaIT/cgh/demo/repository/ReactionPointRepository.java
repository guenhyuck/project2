package com.KoreaIT.cgh.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/*@Mapper 어노테이션을 사용하면 MyBatis가 해당 인터페이스를 구현한 클래스를 자동으로 생성하여 사용할 수 있습니다.
이렇게 생성된 매퍼 클래스는 SQL 매핑 파일(XML 파일)이나 애노테이션을 통해 SQL 쿼리를 정의하고 실행할 수 있게 됩니다.*/
@Mapper
public interface ReactionPointRepository {
	
	/* DB에 select 쿼리문을 이용하여 recationPoint를 RP로 축약하고, 그에 따른 값들을 조회하는 쿼리입니다
	 * 위의 쿼리는 reactionPoint 테이블에서 relTypeCode, relId, memberId 컬럼의 값들을 조건으로 하여 해당하는
	 * 데이터들의 point 컬럼 값을 합산한 결과를 반환합니다. SELECT IFNULL(SUM(RP.point),0):
	 * reactionPoint 테이블에서 RP.point 컬럼의 값들의 합계를 계산합니다. 만약 합계가 NULL인 경우에는 0을 반환합니다.
	 * 합계 계산 결과는 선택한 값으로 반환됩니다.
	 * script를 넣어 동적 sql로 이용합니다
	 */
	
	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{id}
				AND RP.memberId = #{actorId}
			</script>
			""")
	public int getSumReactionPointByMemberId(int actorId, String relTypeCode, int id);
	
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 좋아요를 증가시키는 메소드입니다
	 * reactionPoint 테이블에 입력된 값의 글에
	 * 포인트 1을 증가시킵니다
	 * script를 넣어 동적 sql로 이용합니다
	 */
	
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{id},
				memberId = #{actorId},
				`point` = 1
			</script>
			""")
	public int addGoodReactionPoint(int actorId, String relTypeCode, int id);
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 싫어요를 증가시키는 메소드입니다
	 * reactionPoint 테이블에 입력된 값의 글에
	 * 포인트 -1을 증가시킵니다
	 * script를 넣어 동적 sql로 이용합니다
	 */
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{id},
				memberId = #{actorId},
				`point` = -1
			</script>
			""")
	public int addBadReactionPoint(int actorId, String relTypeCode, int id);
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 좋아요를 취소시키는 메소드입니다
	 * reactionPoint 테이블에 입력된 값의 글에
	 * 적용되었던 값을 없앱니다
	 */

	@Delete("""
			DELETE FROM reactionPoint
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			AND memberId = #{actorId}
			""")
	public void deleteGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 싫어요를 취소시키는 메소드입니다
	 * reactionPoint 테이블에 입력된 값의 글에
	 * 적용되었던 값을 없앱니다
	 */

	@Delete("""
			DELETE FROM reactionPoint
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			AND memberId = #{actorId}
			""")
	public void deleteBadReactionPoint(int actorId, String relTypeCode, int relId);
}