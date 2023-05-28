package com.KoreaIT.cgh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cgh.demo.service.MemberService;
import com.KoreaIT.cgh.demo.util.Ut;
import com.KoreaIT.cgh.demo.vo.Member;
import com.KoreaIT.cgh.demo.vo.Rq;


/*관리자에 대한 컨트롤 클래스
컨트롤러 이노테이션으로 컨트롤러를 선언*/


/*이 컨트롤러는 관리자 페이지에서 회원 목록 조회와 로그아웃 기능을 처리합니다.
/adm/memberAndArticle/list 요청으로 회원 목록을 조회하고,
/adm/member/doLogout 요청으로 로그아웃을 처리합니다.*/



@Controller
public class AdmMemberAndArticleController {

	/*
	 * @Autowired: 스프링에 의해 자동으로 의존성 주입을 받습니다. 
	 * MemberService memberService: MemberService 인터페이스를 구현한 서비스 객체입니다.
	 *  Rq rq: Rq 객체를 주입받습니다.
	 */
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	/*
	 * @RequestMapping: 요청 URL과 해당 메서드를 매핑합니다. showList(): 회원 목록을 조회하는 메서드입니다. 매개변수로
	 * 모델(Model) 객체와 필요한 파라미터들을 받습니다.
	 * showList(): 회원 목록을 조회하는 메서드입니다.
	 *  매개변수로 모델(Model) 객체와 필요한 파라미터들을 받습니다.
	 */
	@RequestMapping("/adm/memberAndArticle/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") String authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {
         
		
		
		/*
		 * memberService.getMembersCount(): 회원 수를 가져오는 메서드입니다. 회원 수를 계산하기 위해 필요한 파라미터들을
		 * 전달합니다. 페이지네이션 처리를 위해 한 페이지에 보여줄 회원 수 (itemsInAPage)와 전체 페이지 수 (pagesCount)를
		 * 계산합니다.
		 */
		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;
		int pagesCount = (int) Math.ceil((double) membersCount / itemsInAPage);
		
		
		
		/*
		 * memberService.getForPrintMembers(): 조건에 맞는 회원 목록을 조회하는 메서드입니다. 필요한 파라미터들을
		 * 전달하고, 페이지네이션 정보도 함께 전달합니다. 조회한 회원 목록과 관련된 데이터를 모델에 추가합니다.
		 */
		List<Member> members = memberService.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				itemsInAPage, page);

		model.addAttribute("authLevel", authLevel);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("membersCount", membersCount);
		model.addAttribute("pagesCount", pagesCount);

		model.addAttribute("members", members);
		
		
		/*
		 * 값을 받아와서 클라이언트 요청에 adm/memberAndArticle/list 로 반환합니다
		 */

		return "adm/memberAndArticle/list";
	}

	/*
	 * doLogout(): 로그아웃을 처리하는 메서드입니다. 매개변수로 로그아웃 후 이동할 URI를 받습니다.
	 * @ResponseBody 어노테이션을 사용하여 문자열 형태의 결과를 반환합니다.
	 */
	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();
		
		/*
		 * rq.logout(): Rq 객체의 logout() 메서드를 호출하여 로그아웃 처리를 수행합니다. 로그아웃 후 이동할 URI를 반환합니다.
		 * Ut.jsReplace() 메서드를 사용하여 JavaScript 코드로 변환하여 반환합니다.
		 */

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}
}