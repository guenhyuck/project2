package com.KoreaIT.cgh.demo.vo;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import com.KoreaIT.cgh.demo.service.MemberService;
import com.KoreaIT.cgh.demo.util.Ut;
import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)

/* Rq 클래스는 HTTP 요청과 관련된 정보를 처리하는 클래스입니다. */
public class Rq {
	/*
	 * isAjax, isLogined, loginedMemberId, loginedMember은 어노테이션 Getter를 이용하여 클래스
	 * 내부에서 사용되는 필드입니다.
	 */
	@Getter
	boolean isAjax;
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;
    
	
	/*
	 * HttpServletRequest, HttpServletResponse, MemberService를 매개변수로 받는 생성자입니다. 전달받은
	 * 매개변수들을 사용하여 필드와 세션 정보를 초기화합니다. 요청 URI에 따라 isAjax 값을 설정합니다
	 */
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		paramMap = Ut.getParamMap(req);
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;
		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		this.req.setAttribute("rq", this);

		String requestUri = req.getRequestURI();

		boolean isAjax = requestUri.endsWith("Ajax");

		if (isAjax == false) {
			if (paramMap.containsKey("ajax") && paramMap.get("ajax").equals("Y")) {
				isAjax = true;
			} else if (paramMap.containsKey("isAjax") && paramMap.get("isAjax").equals("Y")) {
				isAjax = true;
			}
		}
		if (isAjax == false) {
			if (requestUri.contains("/get")) {
				isAjax = true;
			}
		}
		this.isAjax = isAjax;

	}
	/*
	 * printHistoryBackJs() 메서드: 주어진 메시지와 함께 브라우저에서 이전 페이지로 이동하는 JavaScript 코드를
	 * 출력합니다.
	 */

	public void printHistoryBackJs(String msg) throws IOException {

		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack("F-B", msg));
	}
	/*
	 * print() 메서드: 주어진 문자열을 출력합니다.
	 */

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * println() 메서드: 주어진 문자열과 개행 문자를 출력합니다.
	 */

	public void println(String str) {
		print(str + "\n");
	}

	/*
	 * login() 메서드: 회원 로그인 시 세션에 로그인된 회원의 ID를 저장합니다.
	 */
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}
	
	/*
	 * logout() 메서드: 로그아웃 시 세션에서 로그인된 회원의 ID를 제거합니다.
	 */
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	/*
	 * jsHistoryBackOnView() 메서드: 뷰 템플릿에서 이전 페이지로 이동하는 JavaScript 코드를 생성하기 위해 필요한
	 * 정보를 설정하고, "usr/common/js" 뷰 템플릿의 경로를 반환합니다.
	 */

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}
	/*
	 * jsHistoryBack() 메서드: 주어진 결과 코드와 메시지를 사용하여 이전 페이지로 이동하는 JavaScript 코드를 생성합니다.
	 */

	public String jsHistoryBack(String resultCode, String msg) {
		return Ut.jsHistoryBack(resultCode, msg);
	}
	/*
	 * jsReplace() 메서드: 주어진 메시지와 URI를 사용하여 페이지를 새로고침하거나 지정된 URI로 이동하는 JavaScript 코드를
	 * 생성합니다.
	 */

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	/*
	 * getCurrentUri() 메서드: 현재 요청의 URI를 반환합니다.
	 */

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		System.out.println(currentUri);
		System.out.println(queryString);
		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}
		System.out.println(currentUri);
		return currentUri;
	}

	// Rq 객체 생성 유도
	// 삭제 x, BeforeActionInterceptor 에서 강제 호출
	
	/*
	 * initOnBeforeActionInterceptor() 메서드: BeforeActionInterceptor에서 Rq 객체를 생성하기 위해
	 * 강제로 호출하는 메서드입니다.
	 */
	public void initOnBeforeActionInterceptor() {
	}
    
	
	/*
	 * isNotLogined() 메서드: 로그인되지 않은 상태인지 여부를 반환합니다.
	 */
		
	public boolean isNotLogined() {
		return !isLogined;
	}
	
	/*
	 * run() 메서드: 테스트용 메서드로, "===========================run A"를 출력합니다.
	 */

	public void run() {
		System.out.println("===========================run A");
	}
	
	/*
	 * jsprintReplace() 메서드: 주어진 메시지와 대체 URI를 사용하여 페이지를 대체하는 JavaScript 코드를 출력합니다.
	 */
	public void jsprintReplace(String msg, String replaceUri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, replaceUri));
	}
	
	/*
	 * getJoinUri() 메서드: 회원가입 URI를 반환합니다.
	 */

	public String getJoinUri() {
		return "/usr/member/join?afterLoginUri=" + getAfterLoginUri();
	}
	
	/*
	 * getJoinUri() 메서드: 로그인 URI를 반환합니다.
	 */

	public String getLoginUri() {
		return "/usr/member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	/*
	 * getJoinUri() 메서드: 로그아웃 URI를 반환합니다.
	 */
	public String getLogoutUri() {

		String requestUri = req.getRequestURI();
		switch (requestUri) {
		case "/usr/article/write":
			return "../member/doLogout?afterLogoutUri=" + "/";
		case "/adm/memberAndArticle/list":
			return "../member/doLogout?afterLogoutUri=" + "/";
		}

		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();

	}
	
//	로그아웃 후 접근 불가 페이지

	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	public String getAfterLoginUri() {
//		로그인 후 접근 불가 페이지

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}
		return getEncodedCurrentUri();
	}
	/*
	 * getEncodedCurrentUri() 메서드: 현재 URI를 인코딩하여 반환하는 메서드입니다.
	 * Ut.getEncodedCurrentUri(getCurrentUri())를 호출하여 현재 URI를 인코딩한 값이 반환됩니다.
	 */

	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
	/*
	 * getArticleDetailUriFromArticleList(Article article) 메서드:
	 * 
	 * 게시글 목록에서 특정 게시글의 상세 페이지 URI를 생성하는 메서드입니다. "/usr/article/detail?id=" +
	 * article.getId() + "&listUri=" + getEncodedCurrentUri()를 통해 특정 게시글의 id와 목록으로
	 * 돌아갈 URI를 조합하여 반환합니다.
	 */

	public String getArticleDetailUriFromArticleList(Article article) {
		return "/usr/article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}
	
	/*
	 * getFindLoginIdUri() 메서드:
	 * 
	 * 로그인 아이디 찾기 페이지의 URI를 반환하는 메서드입니다.
	 * "/usr/member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri()를 통해
	 * 로그인 아이디 찾기 후 돌아갈 URI를 조합하여 반환합니다
	 */

	public String getFindLoginIdUri() {
		return "/usr/member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri();
	}


	/*
	 * getAfterFindLoginIdUri() 메서드:
	 * 
	 * 로그인 아이디 찾기 후 돌아갈 URI를 반환하는 메서드입니다. getEncodedCurrentUri()를 호출하여 현재 URI를 인코딩한
	 * 값이 반환됩니다.
	 */
	private String getAfterFindLoginIdUri() {
		return getEncodedCurrentUri();
	}
	
	/*
	 * getFindLoginPwUri() 메서드:
	 * 
	 * 로그인 비밀번호 찾기 페이지의 URI를 반환하는 메서드입니다.
	 * "/usr/member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri()를 통해
	 * 로그인 비밀번호 찾기 후 돌아갈 URI를 조합하여 반환합니다
	 */

	public String getFindLoginPwUri() {
		return "/usr/member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri();
	}

	/*
	 * getAfterFindLoginPwUri() 메서드:
	 * 
	 * 로그인 비밀번호 찾기 후 돌아갈 URI를 반환하는 메서드입니다. getEncodedCurrentUri()를 호출하여 현재 URI를 인코딩한
	 * 값이 반환됩니다.
	 */
	private String getAfterFindLoginPwUri() {
		return getEncodedCurrentUri();
	}
	/*
	 * isAdmin() 메서드:
	 * 
	 * 현재 사용자가 관리자인지 여부를 반환하는 메서드입니다. isLogined가 false일 경우 false를 반환하고, 그렇지 않은 경우
	 * loginedMember.isAdmin()을 호출하여 사용자의 관리자 여부를 반환합니다.
	 */

	public boolean isAdmin() {
		if (isLogined == false) {
			return false;
		}

		return loginedMember.isAdmin();
	}

}