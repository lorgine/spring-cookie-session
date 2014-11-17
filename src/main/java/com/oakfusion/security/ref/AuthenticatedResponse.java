package com.oakfusion.security.ref;

import com.oakfusion.security.cookie.CookieBaker;
import com.oakfusion.security.serial.AuthSerializer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;

import javax.servlet.http.HttpServletResponse;

final class AuthenticatedResponse extends SaveContextOnUpdateOrErrorResponseWrapper {

	private final AuthCookieConfig config;
	private final AuthSerializer serializer;

	public AuthenticatedResponse(HttpServletResponse response,
								 AuthCookieConfig config,
								 AuthSerializer serializer) {
		this(response, false, config, serializer);
	}

	public AuthenticatedResponse(HttpServletResponse response,
								 boolean disableUrlRewriting,
								 AuthCookieConfig config,
								 AuthSerializer serializer) {
		super(response, disableUrlRewriting);
		this.config = config;
		this.serializer = serializer;
	}

	@Override
	protected void saveContext(SecurityContext context) {
		if (!this.isContextSaved()) {
			addCookie(
					new CookieBaker(serializer.write(context.getAuthentication()))
						.withPath(config.getCookiePath())
						.withName(config.getCookieName())
						.expires(CookieBaker.ExpirationBuilder.never())
						.httpOnly()
						.secure()
						.bake()
			);
		}
	}
}
