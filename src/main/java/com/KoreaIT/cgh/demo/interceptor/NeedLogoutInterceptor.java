package com.KoreaIT.cgh.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.KoreaIT.cgh.demo.vo.Rq;
@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {
	
	
    /*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다.  
	 * Rq rq: Rq 객체를 주입받습니다.
	 */
	
	
	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		if (rq.isLogined()) {

			rq.printHistoryBackJs("로그아웃 후 이용해 주세요");
			return false;
		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}