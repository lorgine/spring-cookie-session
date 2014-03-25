package com.oakfusion.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SecurityCookieService {

	private final String cookieName;
	private final Codec codec;
	private final AuthenticationSerializer serializer;

	public SecurityCookieService(String cookieName, String key) {
		this.cookieName = cookieName;
		this.codec = new AESCodec(key);
		this.serializer = new AuthenticationSerializer();
	}

	public Authentication getAuthenticationFrom(Cookie cookie) {
		byte[] decodedFromBase64 = Base64.decodeBase64(cookie.getValue());
		byte[] decryptedAuthentication = codec.decrypt(decodedFromBase64);

		return serializer.deserializeFrom(decryptedAuthentication);
	}

	public Cookie createSecurityCookie(Authentication auth) {
		byte[] serializedAuthentication = serializer.serializeToByteArray(auth);
		byte[] encryptedAuthentication = codec.encrypt(serializedAuthentication);
		byte[] encodedWithBase64 = Base64.encodeBase64URLSafe(encryptedAuthentication);

		String value = new String(encodedWithBase64);
		Cookie c = new Cookie(cookieName, value);
		c.setPath("/");

		return c;
	}

	public Cookie getSecurityCookieFrom(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}

		return null;
	}

	public Cookie createLogoutCookie() {
		Cookie c = new Cookie(cookieName, "");
		c.setPath("/");
		c.setMaxAge(0);
		return c;
	}

	public boolean containsSecurityCookie(HttpServletRequest request) {
		return getSecurityCookieFrom(request) != null;
	}
}
