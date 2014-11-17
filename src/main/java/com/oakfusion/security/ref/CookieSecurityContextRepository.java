package com.oakfusion.security.ref;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieSecurityContextRepository implements SecurityContextRepository {

	private final AuthCookieConfig config;

	public CookieSecurityContextRepository(AuthCookieConfig config) {
		this.config = config;
	}

	@Override
	public SecurityContext loadContext(final HttpRequestResponseHolder requestResponseHolder) {

		AuthenticatedResponse response = new AuthenticatedResponse(requestResponseHolder.getResponse(), config);
		requestResponseHolder.setResponse(response);

		final AuthenticatedRequest authRequest = new AuthenticatedRequest(requestResponseHolder.getRequest());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authRequest.getAuthentication());
		return context;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		AuthenticatedResponse responseWrapper = (AuthenticatedResponse) response;

		if (!responseWrapper.isContextSaved()) {
			responseWrapper.saveContext(context);
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		return new AuthenticatedRequest(request).containsAuthentication();
	}

}
