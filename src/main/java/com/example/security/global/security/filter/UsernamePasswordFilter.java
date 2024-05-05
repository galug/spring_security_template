package com.example.security.global.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.security.global.common.exception.BaseException;
import com.example.security.global.common.response.BaseResponse;
import com.example.security.global.common.response.BaseResponseStatus;
import com.example.security.global.security.details.CustomUserDetails;
import com.example.security.global.security.dto.TokenDto;
import com.example.security.global.security.provider.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UsernamePasswordFilter extends AbstractAuthenticationProcessingFilter {
	private final TokenProvider tokenProvider;
	private final ObjectMapper objectMapper;

	public UsernamePasswordFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager,
		TokenProvider tokenProvider) {
		super(defaultFilterProcessesUrl, authenticationManager);
		this.tokenProvider = tokenProvider;
		objectMapper = new ObjectMapper();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException,
		IOException {
		String method = request.getMethod();
		logger.info("UsernamePasswordFilter===============");

		if (!method.equals("POST")) {
			throw new BaseException(BaseResponseStatus.WRONG_METHOD_EXCEPTION);
		}

		logger.info("method equals ===============");
		ServletInputStream inputStream = request.getInputStream();

		LoginRequestDto loginRequestDto = new ObjectMapper().readValue(inputStream, LoginRequestDto.class);
		Authentication authenticate = this.getAuthenticationManager()
			.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequestDto.id,
				loginRequestDto.password
			));
		logger.info("UsernamePasswordFilter After");
		logger.info(authenticate.getPrincipal());
		logger.info(authenticate.getCredentials());
		logger.info(authenticate.getAuthorities());
		return authenticate;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		this.getSuccessHandler().onAuthenticationSuccess(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		this.getFailureHandler().onAuthenticationFailure(request, response, failed);
	}

	public record LoginRequestDto(
		String id,
		String password
	) {
	}
}
