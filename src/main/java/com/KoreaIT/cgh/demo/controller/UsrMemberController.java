package com.KoreaIT.cgh.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cgh.demo.service.MemberService;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Member;
import com.KoreaIT.cgh.demo.vo.ResultData;
import com.KoreaIT.cgh.demo.vo.Rq;



//회원에 대한 컨트롤 클래스
//컨트롤러 이노테이션으로 컨트롤러를 선언

/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/


@Controller
public class UsrMemberController {
	
	/*
	 * 필드선언부
	 * 
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다. ReplyService replyService:
	 * ReplyService 인터페이스를 구현한 서비스 객체입니다. ArticleService articleService:
	 * ArticleService 인터페이스를 구현한 서비스 객체입니다. Rq rq: Rq 객체를 주입받습니다.
	 * ReactionPointService reactionPointService: ReactionPointService 인터페이스를 구현한
	 * 서비스 객체입니다.
	 */
	
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	/* @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. */
	
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		/* 클라이언트 요청에 usr/member/join로 반환합니다 */
		return "usr/member/join";
	}
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	/* loginId를 파라미터로 받습니다. */
	public ResultData getLoginIdDup(String loginId) {
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		Member existsMember = memberService.getMemberByLoginId(loginId);
		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}
		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	/* loginId,loginPw,name,nickname,cellphoneNum,email,afterLoginUri를 파라미터로 받습니다. */
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

//		if (Ut.empty(loginId)) {
//			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
//		}
//		if (Ut.empty(loginPw)) {
//			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
//		}
//		if (Ut.empty(name)) {
//			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
//		}
//		if (Ut.empty(nickname)) {
//			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
//		}
//		if (Ut.empty(cellphoneNum)) {
//			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
//		}
//		if (Ut.empty(email)) {
//			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
//		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
  
		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}
		Member member = memberService.getMemberById(joinRd.getData1());
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);
		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		/* 클라이언트 요청에 usr/member/login로 반환합니다 */
		return "usr/member/login";
	}
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	/* loginId,loginPw,afterLoginUri를 파라미터로 받습니다. */
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다");
		}
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		Member member = memberService.getMemberByLoginId(loginId);
		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		System.out.println(Ut.sha256(loginPw));

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다!!!!!"));
		}

		rq.login(member);
  
		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), afterLoginUri);
	}
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	/* afterLogoutUri 를 파라미터로 받습니다. */
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {
		rq.logout();
		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		/* 클라이언트 요청에 usr/member/myPage로 반환합니다 */
		return "usr/member/myPage";
	}
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		/* 클라이언트 요청에 usr/member/checkPw로 반환합니다 */
		return "usr/member/checkPw";
	}
	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	/* loginPw, replaceUri를 파라미터로 받습니다. */
	public String doCheckPw(String loginPw, String replaceUri) {
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBackOnView("비밀번호 입력해");
		}

		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("", "비밀번호 틀림");
		}
  
		return rq.jsReplace("", replaceUri);
	}
	@RequestMapping("/usr/member/modify")
	public String showModify() {
		/* 클라이언트 요청에 usr/member/modify로 반환합니다 */
		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	/* loginId,loginPw,name,nickname,cellphoneNum,email,afterLoginUri를 파라미터로 받습니다. */
	public String doModify(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		Member member = memberService.getMemberByLoginId(loginId);
		
		/* 파라미터 값을 입력받지 못했다면 해당 코드와 메세지를 보여줍니다 */

		if (Ut.empty(loginPw)) {
			loginPw = member.getLoginPw();
			/* 그게 아니라면 입력 된 loginPw를 sha256알고리즘으로 변환합니다 */
		} else {
			loginPw = Ut.sha256(loginPw);
		}
//		if (Ut.empty(name)) {
//			return rq.jsHitoryBackOnView("name 입력해");
//		}
//		if (Ut.empty(nickname)) {
//			return rq.jsHitoryBackOnView("nickname 입력해");
//		}
//		if (Ut.empty(cellphoneNum)) {
//			return rq.jsHitoryBackOnView("cellphoneNum 입력해");
//		}
//		if (Ut.empty(email)) {
//			return rq.jsHitoryBackOnView("email 입력해");
//		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {
		/* 클라이언트 요청에 usr/member/findLoginId로 반환합니다 */

		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	/* afterFindLoginIdUri, name,email를 파라미터로 받습니다. */
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name,
			String email) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "존재하지 않는 회원입니다");
		}
  
		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace("S-1", Ut.f("회원님의 아이디는 [ %s ] 입니다", member.getLoginId()), afterFindLoginIdUri);
	}

	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {
		/* 클라이언트 요청에 usr/member/findLoginPw로 반환합니다 */

		return "usr/member/findLoginPw";
	}

	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	/* afterFindLoginPwUri, loginId,email를 파라미터로 받습니다. */
	public String doFindLoginPw(@RequestParam(defaultValue = "/") String afterFindLoginPwUri, String loginId,
			String email) {

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "존재하지 않는 회원입니다");
		}

		if (member.getEmail().equals(email) == false) {
			return Ut.jsHistoryBack("F-2", "일치하는 이메일이 없습니다");
		}

		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmail(member);

		/*Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다*/
		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getResultCode(), notifyTempLoginPwByEmailRd.getMsg(),
				afterFindLoginPwUri);
	}

}