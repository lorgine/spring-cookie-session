package com.oakfusion.security.cookie;

import javax.servlet.http.Cookie;

public class CookieBaker {
	private String cookiePath;
	private String cookieName;
	private ExpirationBuilder expiration;
	private boolean httpOnly;
	private boolean isSecure;

	public CookieBaker(byte[] content) {

	}

	public Cookie bake() {
		return null;
	}

	public CookieBaker withPath(String cookiePath) {
		this.cookiePath = cookiePath;
		return this;
	}

	public CookieBaker withName(String cookieName) {
		this.cookieName = cookieName;
		return this;
	}

	public CookieBaker expires(ExpirationBuilder expiration) {

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

	public static final class ExpirationBuilder {
		public static ExpirationBuilder never() {
			return null;
		}
	}
}
