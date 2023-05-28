package com.KoreaIT.cgh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.cgh.demo.vo.Rq;


//홈 화면에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언

/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
public class UsrHomeController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	private Rq rq;
	
	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	@RequestMapping("/usr/home/beforeMain")
	public String showbeforeMain() {
		rq.run();
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/home/beforeMain로 반환합니다
		 */
		return "usr/home/beforeMain";
	}
	@RequestMapping("/usr/home/main")
	public String showMain() {
		rq.run();
		/*
		 * 값을 받아와서 클라이언트 요청에 usr/home/main 로 반환합니다
		 */
		return "usr/home/main";
	}

	@RequestMapping("/")
	public String showRoot() {
		/*
		 * 기본값으로 설정한 홈페이지는 메인입니다
		 *  클라이언트 요청에 /usr/home/main 로 반환합니다
		 */
		return "redirect:/usr/home/main";
	}

}