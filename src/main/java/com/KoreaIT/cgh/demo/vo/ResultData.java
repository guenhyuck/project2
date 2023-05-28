package com.KoreaIT.cgh.demo.vo;

import lombok.Getter;

/*ResultData 클래스는 제네릭 타입 DT를 사용하여 결과 데이터를 저장하는 역할을 합니다*/

public class ResultData<DT> {
	
	/*
	 * resultCode, msg, data1, data1Name, data2, data2Name은 어노테이션 Getter를 이용하여 클래스
	 * 내부에서 사용되는 필드입니다.
	 */
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private DT data1;
	@Getter
	private String data1Name;
	@Getter
	private Object data2;
	@Getter
	private String data2Name;

	/*
	 * from() 메서드: 결과 데이터 객체를 생성하여 반환하는 정적 메서드입니다. resultCode, msg, data1Name,
	 * data1을 매개변수로 받아 ResultData 객체를 생성하고 필드에 값을 설정한 후 반환합니다.
	 */

	public static <DT> ResultData<DT> from(String resultCode, String msg) {
		return from(resultCode, msg, null, null);
	}
	

	

	public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
		ResultData<DT> rd = new ResultData<DT>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;

		return rd;
	}
	
	/*
	 * isSuccess() 메서드: 결과 코드가 "S-"로 시작하는지 여부를 판단하여 불린 값으로 반환하는 메서드입니다.
	 */

	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	/*
	 * isFail() 메서드: isSuccess() 메서드의 반대 결과를 반환하는 메서드입니다.
	 */

	public boolean isFail() {
		return isSuccess() == false;
	}
	
	/*
	 * newData() 메서드: 새로운 데이터를 포함한 ResultData 객체를 생성하여 반환하는 정적 메서드입니다. 기존 ResultData
	 * 객체의 결과 코드, 메시지를 그대로 사용하고, 새로운 데이터의 이름과 값을 매개변수로 받아 ResultData 객체를 생성하고 반환합니다.
	 */

	public static <DT> ResultData<DT> newData(ResultData rd, String data1Name, DT newData) {
		return from(rd.getResultCode(), rd.getMsg(), data1Name, newData);
	}
	
	/*
	 * setData2() 메서드: 두 번째 데이터와 데이터의 이름을 설정하는 메서드입니다. data2Name과 data2 필드에 값을
	 * 설정합니다.
	 */

	public void setData2(String data2Name,Object data2) {
		this.data2Name = data2Name;
		this.data2 = data2;
		
		
	}
   
}