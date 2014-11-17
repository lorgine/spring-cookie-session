package com.oakfusion.security.ref;

import com.oakfusion.security.SecurityCookieService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieSecurityContextRepository implements SecurityContextRepository {

	private final SecurityCookieService securityCookieService;

	public CookieSecurityContextRepository(SecurityCookieService securityCookieService) {
		this.securityCookieService = securityCookieService;
	}

	@Override
	public SecurityContext loadContext(final HttpRequestResponseHolder requestResponseHolder) {

		SaveToCookieResponseWrapper response = new SaveToCookieResponseWrapper(requestResponseHolder.getResponse(), false);
		requestResponseHolder.setResponse(response);

		final AuthenticatedRequest authRequest = new AuthenticatedRequest(requestResponseHolder.getRequest());

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authRequest.getAuthentication());
		return context;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		SaveToCookieResponseWrapper responseWrapper = (SaveToCookieResponseWrapper) response;

		if (!responseWrapper.isContextSaved()) {
			responseWrapper.saveContext(context);
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		return securityCookieService.containsSecurityCookie(request);
	}

	final class SaveToCookieResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {

		public SaveToCookieResponseWrapper(HttpServletResponse response, boolean disableUrlRewriting) {
			super(response, disableUrlRewriting);
		}

		@Override
		protected void saveContext(SecurityContext context) {
			Cookie securityCookie = securityCookieService.createSecurityCookie(context.getAuthentication());
			if (securityCookie != null) {
				if (!this.isContextSaved()) {
					addCookie(securityCookie);
				}
			} else {
				addCookie(securityCookieService.createLogoutCookie());
			}
		}
	}
}
