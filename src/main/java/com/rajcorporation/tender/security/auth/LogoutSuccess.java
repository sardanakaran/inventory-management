package com.rajcorporation.tender.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogoutSuccess implements LogoutSuccessHandler {

	@Autowired
	ObjectMapper objectMapper;

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String, String> result = new HashMap<>();

		result.put("result", "success");
		response.setContentType("application/json");
		response.getWriter().write(objectMapper.writeValueAsString(result));

		for (Cookie cookie : httpServletRequest.getCookies()) {
			if (cookie.getName().equalsIgnoreCase(TOKEN_COOKIE)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		response.setStatus(HttpServletResponse.SC_OK);

	}

}