package com.KoreaIT.cgh.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.cgh.demo.vo.Board;

/*@Mapper 어노테이션을 사용하면 MyBatis가 해당 인터페이스를 구현한 클래스를 자동으로 생성하여 사용할 수 있습니다.
이렇게 생성된 매퍼 클래스는 SQL 매핑 파일(XML 파일)이나 애노테이션을 통해 SQL 쿼리를 정의하고 실행할 수 있게 됩니다.*/
@Mapper
public interface BoardRepository {
	
	/*
	 * 아래 코드는 board 테이블에서 id가 boardId와 일치하고 delStatus가 0인 레코드를 선택하는 SELECT 쿼리를 정의한
	 * 것입니다
	 */

	@Select("""
			SELECT *
			FROM board
			WHERE id= #{boardId}
			AND delStatus = 0;
			""")
	Board getBoardById(int boardId);

}