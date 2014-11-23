package com.oakfusion.security.cookie;

import javax.servlet.http.Cookie;

public class CookieBaker {
	private final String content;
	private final String cookieName;
	private String cookiePath = "/";
	private int expiration = ExpirationBuilder.session();
	private boolean httpOnly = true;
	private boolean isSecure = false;


	public CookieBaker(String content, String cookieName) {
		this.content = content;
		this.cookieName = cookieName;
	}

	public CookieBaker(byte[] content, String cookieName) {
		this(new String(content), cookieName);
	}

	public Cookie bake() {
		final Cookie cookie = new Cookie(cookieName, content);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(expiration);
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(isSecure);
		return cookie;
	}

	public CookieBaker withPath(String cookiePath) {
		this.cookiePath = cookiePath;
		return this;
	}

	public CookieBaker expires(int expiration) {
		this.expiration = expiration;
		return this;
	}

	public CookieBaker httpOnly() {
		this.httpOnly = true;
		return this;
	}

	public CookieBaker secure() {
		this.isSecure = true;
		return this;
	}

	@SuppressWarnings("UnusedDeclaration")
	public static final class ExpirationBuilder {
		public static int never() { return Integer.MAX_VALUE;}
		public static int now() { return 0;}
		public static int session() { return -1;}
		public static int inSeconds(int seconds) { return seconds;}
		public static int inMinutes(int minutes) { return minutes * 60;}
		public static int inHours(int hours) { return hours * 60 * 60;}
		public static int inDays(int days) { return days * 24 * 60 * 60;}

	}
}
