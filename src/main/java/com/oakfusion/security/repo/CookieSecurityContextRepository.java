package com.oakfusion.security.repo;

import com.oakfusion.security.serial.AuthSerializer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieSecurityContextRepository implements SecurityContextRepository {

	private final AuthCookieConfig config;
	private final AuthSerializer serializer;

	public CookieSecurityContextRepository(AuthCookieConfig config, AuthSerializer serializer) {
		this.config = config;
		this.serializer = serializer;
	}

	@Override
	public SecurityContext loadContext(final HttpRequestResponseHolder requestResponseHolder) {

		AuthenticatedResponse response = new AuthenticatedResponse(requestResponseHolder.getResponse(), config, serializer);
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
