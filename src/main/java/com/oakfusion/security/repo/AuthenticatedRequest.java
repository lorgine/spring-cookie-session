package com.oakfusion.security.repo;

import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthenticatedRequest {

	private final HttpServletRequest request;

	public AuthenticatedRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Authentication getAuthentication() {
		if (request.getCookies() != null) {
			Cookie securityCookie = getSecurityCookieFrom(request);
			if (securityCookie != null) {

				return null;//securityCookieService.getAuthenticationFrom(securityCookie);



			}
		}
		return null;
	}

	public Cookie getSecurityCookieFrom(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = "";
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}

		return null;
	}

	public boolean containsAuthentication() {
		return false;
	}
}
