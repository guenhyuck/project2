package com.KoreaIT.cgh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.cgh.demo.vo.Rq;


//날씨에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언



/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
public class WeatherController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	private Rq rq;
	
	public WeatherController(Rq rq) {
		this.rq = rq;
	}
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	/*
	 * 값을 받아와서 클라이언트 요청에 /weather/weather/weather , weather2 로 반환합니다
	 */
	
	// 날씨 (보여주기식)
	@RequestMapping("/weather/weather/weather")
	public String showweather() {
		rq.run();
		return "/weather/weather/weather";
	}
	// 날씨 (실시간)
	@RequestMapping("/weather/weather/weather2")
	public String showweather2() {
		rq.run();
		return "/weather/weather/weather2";
	}
	
	
	  @GetMapping("/weather")
	    public String showWeatherPopup() {
	        return "weather"; // 팝업 창에 대한 JSP/Thymeleaf/HTML 파일의 이름을 반환합니다.
	    }

}