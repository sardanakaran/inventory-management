package com.rajcorporation.tender.security.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajcorporation.tender.security.TokenHelper;
import com.rajcorporation.tender.security.auth.model.Authority;
import com.rajcorporation.tender.security.auth.model.User;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${jwt.expires_in}")
	private int EXPIRES_IN;

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		User user = (User) authentication.getPrincipal();

		String jws = tokenHelper.generateToken(user.getUsername());

		// Create token auth Cookie
		Cookie authCookie = new Cookie(TOKEN_COOKIE, (jws));
		authCookie.setPath("/tender");
		authCookie.setHttpOnly(true);

		authCookie.setMaxAge(EXPIRES_IN);
		// Add cookie to response
		response.addCookie(authCookie);
		// JWT is also in the response
		// UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
		// String jwtResponse = objectMapper.writeValueAsString( userTokenState
		// );
		response.setContentType("application/json");

		AppUser appUser = new AppUser(user.getFirstname(), user.getLastname());

		Collection<Authority> authorities = user.getAuthorities();
		if (authorities != null && !authorities.isEmpty()) {
			authorities.stream().forEach(auth -> {
				appUser.withRole(auth.getAuthority());
			});
		}

		response.getWriter().write(objectMapper.writeValueAsString(appUser));

	}

	static class AppUser {
		String firstName;
		String lastName;
		List<String> roles = new ArrayList<>();

		public AppUser(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public AppUser withRole(String role) {
			this.roles.add(role);
			return this;
		}

		public List<String> getRoles() {
			return roles;
		}

	}

}
