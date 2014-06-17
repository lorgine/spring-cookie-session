package com.oakfusion.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SecurityCookieService {

	private static final String DEFAULT_COOKIE_PATH = "/";
	private final String cookieName;
	private final String cookiePath;
	private final Codec codec;
	private final AuthenticationSerializer serializer;

	public SecurityCookieService(String cookieName, String key, String cookiePath) {
		this.cookieName = cookieName;
		this.cookiePath = cookiePath;
		this.codec = new AESCodec(key);
		this.serializer = new AuthenticationSerializer();
	}

	public SecurityCookieService(String cookieName, String key) {
		this.cookieName = cookieName;
		this.cookiePath = DEFAULT_COOKIE_PATH;
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
		String encodedWithBase64 = Base64.encodeBase64URLSafeString(encryptedAuthentication);

		Cookie c = new Cookie(cookieName, encodedWithBase64);
		c.setPath(cookiePath);

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
		c.setPath(cookiePath);
		c.setMaxAge(0);
		return c;
	}

	public boolean containsSecurityCookie(HttpServletRequest request) {
		return getSecurityCookieFrom(request) != null;
	}
}
