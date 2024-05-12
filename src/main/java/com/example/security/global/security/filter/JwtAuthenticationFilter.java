package com.example.security.global.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.domain.auth.dto.AuthDto;
import com.example.security.domain.auth.service.UserService;
import com.example.security.global.security.provider.TokenProvider;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//	@Value("{spring.datasource.url}")
	private String TOKEN_HEADER = "JWT_TOKEN";
	private final TokenProvider tokenProvider;

	private List<String> notFilteredUrls = Arrays.asList(
		"/auth/signup", "/auth/login"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		logger.info("jwt filter");

		String requestURI = request.getRequestURI();
		logger.info(requestURI);
		logger.info(response.getOutputStream());
		if (notFilteredUrls.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = request.getHeader(TOKEN_HEADER);
		logger.info(TOKEN_HEADER+ ":" + token);
		tokenProvider.validateToken(token);
		Long userId = tokenProvider.resolveToken(token);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		filterChain.doFilter(request, response);
	}
}
