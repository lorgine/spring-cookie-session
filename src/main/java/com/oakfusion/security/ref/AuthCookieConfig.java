package com.oakfusion.security.ref;

public class AuthCookieConfig {

	private final String cookieName;
	private final String key;
	private final String cookiePath;

	public AuthCookieConfig(String cookieName, String key, String cookiePath) {
		this.cookieName = cookieName;
		this.key = key;
		this.cookiePath = cookiePath;
	}

	public String getCookieName() {
		return cookieName;
	}

	public String getKey() {
		return key;
	}

	public String getCookiePath() {
		return cookiePath;
	}
}
