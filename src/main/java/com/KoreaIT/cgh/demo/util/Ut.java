package com.KoreaIT.cgh.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ut {

	/*
	 * 필드선언부
	 * 
	 * 
	 * HttpServletRequest req: HttpServletRequest 객체를 주입받습니다. HttpServletResponse
	 * resp: HttpServletResponse 객체를 주입받습니다.
	 */

	private HttpServletRequest req;
	private HttpServletResponse resp;

	/*
	 * empty(Object obj) 메서드: 주어진 객체가 비어있는지 여부를 확인합니다.
	 *  null인 경우 빈 객체로 간주합니다. 정수인 경우0인지 확인합니다. 
	 *  문자열이 아닌 경우 빈 객체로 간주합니다. 
	 *  문자열인 경우 공백을 제거하고 길이가 0인지 확인합니다.
	 */

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof Integer) {
			return (int) obj == 0;
		}

		if (obj instanceof String == false) {
			return true;
		}

		String str = (String) obj;

		return str.trim().length() == 0;
	}
	
	/*
	 * f(String format, Object... args) 메서드:
	 * 
	 * 문자열 포맷을 사용하여 문자열을 생성합니다. String.format() 메서드를 사용합니다
	 */

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}
	
	/*
	 * jsHistoryBack(String resultCode, String msg) 메서드:
	 * 
	 * 이전 페이지로 이동하는 JavaScript 코드를 생성합니다.
	 *  경고 메시지(msg)가 있을 경우 경고창을 표시합니다.
	 */

	public static String jsHistoryBack(String resultCode, String msg) {

		if (msg == null) {
			msg = "";
		}

		return Ut.f("""
				<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					history.back();
				</script>
				""", msg);
	}
	
	/*
	 * jsReplace(String msg, String uri) 메서드:
	 * 
	 * 지정된 URL로 페이지를 변경하는 JavaScript 코드를 생성합니다. 
	 * 경고 메시지(msg)가 있을 경우 경고창을 표시합니다.
	 */

	public static String jsReplace(String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);

	}
	
	/*
	 * jsReplace(String resultCode, String msg, String uri) 메서드:
	 * 
	 * 지정된 URL로 페이지를 변경하는 JavaScript 코드를 생성합니다. 
	 * 경고 메시지(msg)와 결과 코드(resultCode)가 있을
	 * 경우 경고창을 표시합니다.
	 */

	public static String jsReplace(String resultCode, String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);

	}
	
	/*
	 * jsHistoryBackOnView(HttpServletRequest req, String msg) 메서드:
	 * 
	 * 이전 페이지로 이동하는 JavaScript 코드를 생성하고, 메시지(msg)를 요청 속성에 설정합니다. 
	 * JavaScript코드를 실행할 수 있도록 "usr/common/js"를 반환합니다
	 */

	public static String jsHistoryBackOnView(HttpServletRequest req, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);

		return "usr/common/js";
	}
	
	/*
	 * getEncodedCurrentUri(String currentUri) 메서드:
	 * 
	 * 현재 URI를 인코딩하여 반환합니다. UTF-8으로 인코딩합니다.
	 */

	public static String getEncodedCurrentUri(String currentUri) {

		try {
			return URLEncoder.encode(currentUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return currentUri;
		}

	}
	
	/*
	 * getEncodedUri(String uri) 메서드:
	 * 
	 * 주어진 URI를 인코딩하여 반환합니다. UTF-8으로 인코딩합니다.
	 */
	public static String getEncodedUri(String uri) {

		try {
			return URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return uri;
		}

	}
	
	
	/*
	 * getParamMap(HttpServletRequest req) 메서드:
	 * 
	 * 요청 파라미터의 이름과 값을 담은 맵을 반환합니다.
	 */

	public static Map<String, String> getParamMap(HttpServletRequest req) {
		Map<String, String> param = new HashMap<>();

		Enumeration<String> parameterNames = req.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = req.getParameter(paramName);

			param.put(paramName, paramValue);
		}

		return param;
	}
	
	/*
	 * getAttr(Map map, String attrName, String defaultValue) 메서드:
	 * 
	 * 맵에서 속성(attrName)에 해당하는 값을 가져옵니다. 
	 * 속성이 존재하지 않을 경우 기본값(defaultValue)을 반환합니다.
	 */
		

	public static String getAttr(Map map, String attrName, String defaultValue) {

		if (map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}

		return defaultValue;
	}

	// sha256
	/*
	 * sha256(String input) 메서드:
	 * 
	 * 주어진 문자열을 SHA-256 해시로 변환합니다.
	 * MessageDigest 클래스를 사용하여 해시를 계산합니다. 
	 * 변환된 해시를 16진수 문자열로 반환합니다
	 */
	
	
	public static String sha256(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
/*	getTempPassword(int length) 메서드:

		지정된 길이의 임시 비밀번호를 생성하여 반환합니다.
		영문 소문자와 숫자를 사용하여 임시 비밀번호를 생성합니다.*/

	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}

}