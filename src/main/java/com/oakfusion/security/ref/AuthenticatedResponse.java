package com.oakfusion.security.ref;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;

import javax.servlet.http.HttpServletResponse;

final class AuthenticatedResponse extends SaveContextOnUpdateOrErrorResponseWrapper {

	private final AuthCookieConfig config;

	public AuthenticatedResponse(HttpServletResponse response, AuthCookieConfig config) {
		this(response,false, config);
	}

	public AuthenticatedResponse(HttpServletResponse response, boolean disableUrlRewriting, AuthCookieConfig config) {
		super(response, disableUrlRewriting);
		this.config = config;
	}

	@Override
	protected void saveContext(SecurityContext context) {
		if (!this.isContextSaved()) {
			addCookie(new CookieBaker(context.getAuthentication()).bake());
		}
	}
}
