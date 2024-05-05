package com.example.security.global.security.handler;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.security.global.common.response.BaseResponse;
import com.example.security.global.security.details.CustomUserDetails;
import com.example.security.global.security.dto.TokenDto;
import com.example.security.global.security.provider.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCommonSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final TokenProvider tokenProvider;
	private final ObjectMapper objectMapper;

	public LoginCommonSuccessHandler(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
		objectMapper = new ObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		logger.info("successFulauthentication");
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String[] tokens = tokenProvider.generateTokens(userDetails.getUserId());
		TokenDto tokenDto = new TokenDto(tokens[0], tokens[1]);
		BaseResponse<TokenDto> responseContents = new BaseResponse<>(tokenDto);
		String responseStr = objectMapper.writeValueAsString(responseContents);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseStr);
	}
}
