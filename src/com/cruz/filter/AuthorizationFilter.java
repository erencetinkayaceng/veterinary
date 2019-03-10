package com.cruz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(false);

		String loginURI = request.getContextPath() + "/index.xhtml";

		boolean loggedIn = (session != null) && (session.getAttribute("username") != null);
		boolean loginRequest = request.getRequestURI().equals(loginURI);
		boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + "/secured/*");

		if (loggedIn || loginRequest || resourceRequest) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(loginURI);
		}

	}

	@Override
	public void destroy() {

	}
}
