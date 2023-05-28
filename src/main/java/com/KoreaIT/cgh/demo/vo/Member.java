package com.KoreaIT.cgh.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data: Lombok 라이브러리에서 제공하는 애노테이션으로, 자동으로 게터(Getter), 세터(Setter), toString(), equals(), hashCode() 메서드를 생성합니다.
@AllArgsConstructor: Lombok에서 제공하는 애노테이션으로, 모든 멤버 변수를 인자로 받는 생성자를 자동으로 생성합니다.
@NoArgsConstructor: Lombok에서 제공하는 애노테이션으로, 기본 생성자를 자동으로 생성합니다*/

@Data
@AllArgsConstructor
@NoArgsConstructor

/*
 * 이 코드는 Article 클래스를 나타내는 데이터 모델입니다. 해당 클래스에는 멤버 변수와 메서드가 있습니다.
 */
public class Member {
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private int authLevel;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
	private boolean delStatus;
	private String delDate;

	/*
	 * getForPrintType1RegDate() 메서드:
	 * 
	 * 해당 메서드는 regDate 문자열에서 일부를 추출하여 특정 형식으로 반환하는 역할을 합니다. regDate.substring(2,
	 * 16)은 regDate 문자열에서 인덱스 2부터 15까지의 부분 문자열을 추출합니다. replace(" ", "<br />")는 추출된
	 * 부분 문자열에서 공백을 <br /> 태그로 변경합니다. 최종적으로 변경된 문자열을 반환합니다.
	 */

	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br />");
	}

	/*
	 * getForPrintType1UpdateDate() 메서드:
	 * 
	 * 해당 메서드는 updateDate 문자열에서 일부를 추출하여 특정 형식으로 반환하는 역할을 합니다.
	 * updateDate.substring(2, 16)은 updateDate 문자열에서 인덱스 2부터 15까지의 부분 문자열을 추출합니다.
	 * replace(" ", "<br />")는 추출된 부분 문자열에서 공백을 <br /> 태그로 변경합니다. 최종적으로 변경된 문자열을
	 * 반환합니다.
	 */
	public String getForPrintType1UpdateDate() {
		return updateDate.substring(2, 16).replace(" ", "<br />");
	}

	/*
	 * isAdmin() 메서드:
	 * 
	 * 해당 메서드는 현재 Article 객체가 관리자인지 여부를 판단하여 boolean 값으로 반환합니다. this.authLevel == 7은
	 * authLevel이 7인 경우에만 true를 반환합니다. authLevel은 Article 클래스에 선언되어 있지 않으므로, 해당 코드를
	 * 사용하기 위해서는 Article 클래스에 authLevel 멤버 변수가 존재해야 합니다. 따라서 isAdmin() 메서드의 정확한 동작은
	 * authLevel 멤버 변수의 선언과 초기화에 따라 달라집니다.
	 */
	public boolean isAdmin() {
		return this.authLevel == 7;
	}
}
