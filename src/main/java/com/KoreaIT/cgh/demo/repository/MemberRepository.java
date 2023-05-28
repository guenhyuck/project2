package com.KoreaIT.cgh.demo.repository;

import java.util.List;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

  
import org.apache.ibatis.annotations.Update;
import com.KoreaIT.cgh.demo.vo.Member;


/*@Mapper 어노테이션을 사용하면 MyBatis가 해당 인터페이스를 구현한 클래스를 자동으로 생성하여 사용할 수 있습니다.
이렇게 생성된 매퍼 클래스는 SQL 매핑 파일(XML 파일)이나 애노테이션을 통해 SQL 쿼리를 정의하고 실행할 수 있게 됩니다.*/
@Mapper
public interface MemberRepository {
	
	
	/*
	 * DB에 Insert 쿼리문을 이용하여 회원가입을 실행하는 메소드입니다
	 * member 테이블에 각 변수로 입력한 값이 들어갑니다
	 */
	
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email}
			""")
	void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여member 테이블에서 id를 조회하는 코드입니다..
	 * 입력한 id 값의 회원을 찾는 쿼리문입니다
	 */
	
	@Select("""
			SELECT *
			FROM `member`
			WHERE id = #{id}
			""")
	
	Member getMemberById(int id);
	
	/* 마지막 회원의 id를 조회하는 쿼리입니다 */
	
	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	
	int getLastInsertId();
	
	/*
	 * DB에 select 쿼리문을 이용하여 member 테이블에서 loginid를 조회하는 코드입니다.
	 * 입력한 loginId 값의 회원을 찾는 쿼리문입니다
	 */
	
	@Select("""
			SELECT *
			FROM `member`
			WHERE loginId = #{loginId}
			""")
	
	Member getMemberByLoginId(String loginId);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 member 테이블에서 name과 email를 조회하는 코드입니다.
	 * 입력한 name,email 값의 회원을 찾는 쿼리문입니다
	 */
	
	@Select("""
			SELECT *
			FROM `member`
			WHERE name = #{name}
			AND email = #{email}
			""")
	Member getMemberByNameAndEmail(String name, String email);
	
	
	/*
	 * DB에 update 쿼리문을 이용하여 member 테이블에서 회원 정보를 수정하는 코드입니다.
	 * 입력한 변수 값이 들어가 회원 정보를 수정하는 쿼리문입니다
	 * script로 감싸 동적 sql을 실행합니다
	 */
	
	@Update("""
			<script>
			UPDATE `member`
			<set>
				<if test="loginPw != null">
					loginPw = #{loginPw},
				</if>
				<if test="name != null">
					name = #{name},
				</if>
				<if test="nickname != null">
					nickname = #{nickname},
				</if>
				<if test="cellphoneNum != null">
					cellphoneNum = #{cellphoneNum},
				</if>
				<if test="email != null">
					email = #{email},
				</if>
				updateDate= NOW()
			</set>
			WHERE id = #{id}
			</script>
			""")
	void modify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email);
	
	
	/*
	 * DB에 select 쿼리문을 이용하여 멤버수를 조회하는 코드입니다.
	 * 총 수를 구하여 cnt라는 이름으로 나타내고
	 * member 테이블을 A로 하여 조인문법을 사용했으며
	 * loginId,name,nickname을 검색어 값으로 사용합니다
	 * script로 감싸 동적 sql을 실행합니다
	 * if문으로 공백값을 제외합니다
	 */

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM `member` AS M
			WHERE 1
			<if test="authLevel != 0">
				AND M.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			</script>
							""")
	int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword);
	
	/*
	 * DB에 select 쿼리문을 이용하여 멤버수를 조회하는 코드입니다.
	 * 총 수를 구하여 cnt라는 이름으로 나타내고
	 * member 테이블을 A로 하여 조인문법을 사용했으며
	 * loginId,name,nickname을 검색어 값으로 사용합니다
	 * script로 감싸 동적 sql을 실행합니다
	 * if문으로 공백값을 제외합니다
	 */

	@Select("""
			<script>
			SELECT M.*
			FROM `member` AS M
			WHERE 1
			<if test="authLevel != 0">
				AND M.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			ORDER BY M.id DESC
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
							""")
	List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword,
			int limitStart, int limitTake);

}