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
 * 이 코드는 Article 클래스를 나타내는 데이터 모델입니다. 
 * 해당 클래스에는 멤버 변수가 있습니다.
 */
public class Board {
	private int id;
	private String regDate;
	private String updateDate;
	private String code;
	private String name;
	private boolean delStatus;
	private String delDate;

}
