package com.KoreaIT.cgh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.cgh.demo.vo.Rq;


//설명에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언
@Controller
public class InfoController {
	
	private Rq rq;
	
	public InfoController(Rq rq) {
		this.rq = rq;
	}
	
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	/* 클라이언트 요청에 usr/info/info , info2, info3,info4로 반환합니다 */
 
	@RequestMapping("/usr/info/info")
	public String showInfo() {
		rq.run();
		return "usr/info/info";
	}
	@RequestMapping("/usr/info/info2")
	public String showInfo2() {
		rq.run();
		return "usr/info/info2";
	}
	@RequestMapping("/usr/info/info3")
	public String showInfo3() {
		rq.run();
		return "usr/info/info3";
	}
	@RequestMapping("/usr/info/info4")
	public String showInfo4() {
		rq.run();
		return "usr/info/info4";
	}
	@RequestMapping("/usr/info/forest")
	public String showForest() {
		rq.run();
		return "usr/info/forest";
	}


}