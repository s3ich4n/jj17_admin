package jj17.yubicycle.util;

import java.security.MessageDigest;

/**
 * UID를 가져와 끝에 임의의 문자를 추가해서 그걸 세션으로 쓸 것
 * 향후 여기에 있는 값에 대해 getter,setter를 수행해서
 * 값을 추가할 것
 * @author l4in
 *
 */
public class LoginStatus {

	public static final int SHA256LENGTH	= 64;

	public static String sessionValue = "-1";

	private static String internalSessionValue = null;


	/**
	 * 읽어온 UID값을 토대로 SHA256 해쉬를 돌린다.
	 * 로그인시 해당값을 통해 로그인 된 상태인지 점검한다.
	 *
	 * @param UID
	 */
	public void MakeSHA256Session() {

	    try {

	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(internalSessionValue.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);

	        }

	        internalSessionValue = hexString.toString();

	        //출력
	        System.out.println(hexString.toString());
	        System.out.println(internalSessionValue);

	        // static변수에 내부적으로 처리한 세션값 대입.
	        // 이건 분명 객체지향적이지는 않을듯...
	        sessionValue = internalSessionValue;

	    } catch(Exception ex) {
	    	throw new RuntimeException(ex);
	    }
	}

	/**
	 * static 문자열을 리턴함.
	 * @return
	 */
	public static String getSHA256Session() {
		return sessionValue;
	}

	/**
	 * setter for sessionValue
	 * @param UID
	 */
	public void setSHA256Session(String UID) {
		internalSessionValue = UID;
	}
}
