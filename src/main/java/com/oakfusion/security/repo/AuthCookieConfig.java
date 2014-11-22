package com.oakfusion.security.repo;

public class AuthCookieConfig {

	private final String cookieName;
	private final String key;
	private final String cookiePath;
	private final int cookieExpiresInSeconds;

	public AuthCookieConfig(String cookieName, String key, String cookiePath, int cookieExpiresInSeconds) {
		this.cookieName = cookieName;
		this.key = key;
		this.cookiePath = cookiePath;
		this.cookieExpiresInSeconds = cookieExpiresInSeconds;
	}

	public String getCookieName() {
		return cookieName;
	}

	public int getCookieExpiresInSeconds() {
		return cookieExpiresInSeconds;
	}

	public String getKey() {
		return key;
	}

	public String getCookiePath() {
		return cookiePath;
	}
}
