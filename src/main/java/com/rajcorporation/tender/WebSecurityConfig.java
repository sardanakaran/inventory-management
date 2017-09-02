package com.rajcorporation.tender;

import java.io.BufferedReader;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajcorporation.tender.security.auth.AuthenticationFailureHandler;
import com.rajcorporation.tender.security.auth.AuthenticationSuccessHandler;
import com.rajcorporation.tender.security.auth.LogoutSuccess;
import com.rajcorporation.tender.security.auth.RestAuthenticationEntryPoint;
import com.rajcorporation.tender.security.auth.TokenAuthenticationFilter;
import com.rajcorporation.tender.security.auth.model.LoginRequest;
import com.rajcorporation.tender.service.CustomUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;

	@Bean
	public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
		return new TokenAuthenticationFilter();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private LogoutSuccess logoutSuccess;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	CustomFilter filter;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
				.and().addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.mvcMatchers(HttpMethod.POST, "/**").hasRole("ADMIN").mvcMatchers(HttpMethod.PUT, "/**")
				.hasRole("ADMIN").mvcMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN").antMatchers("/**/admin/**")
				.hasRole("ADMIN").anyRequest().hasAnyRole("ADMIN", "USER").and().formLogin()
				.loginProcessingUrl("/auth/login").successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler).and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")).logoutSuccessHandler(logoutSuccess)
				.deleteCookies(TOKEN_COOKIE, "JSESSIONID");

	}

	@Bean
	public CustomFilter filter() {
		return filter;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**", "/tender/**");
	}

	@Component
	static class CustomFilter extends UsernamePasswordAuthenticationFilter {

		@Autowired
		AuthenticationManager manager;

		@Autowired
		AuthenticationSuccessHandler handler;

		@Autowired
		AuthenticationFailureHandler failureHandler;

		@PostConstruct
		public void init() {
			setAuthenticationManager(manager);
			setAuthenticationFailureHandler(failureHandler);
			setAuthenticationSuccessHandler(handler);
			setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
		}

		private String username;
		private String password;

		@Override
		protected String obtainPassword(HttpServletRequest request) {
			String password = null;

			if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader("Content-Type"))) {
				password = this.password;
			} else {
				password = super.obtainPassword(request);
			}

			return password;
		}

		@Override
		protected String obtainUsername(HttpServletRequest request) {
			String username = null;

			if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader("Content-Type"))) {
				username = this.username;
			} else {
				username = super.obtainUsername(request);
			}

			return username;
		}

		@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
			if (MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader("Content-Type"))) {
				try {

					StringBuffer sb = new StringBuffer();
					String line = null;

					BufferedReader reader = request.getReader();
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}

					ObjectMapper mapper = new ObjectMapper();
					LoginRequest loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);

					this.username = loginRequest.getUsername();
					this.password = loginRequest.getPassword();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return super.attemptAuthentication(request, response);
		}

	}

}