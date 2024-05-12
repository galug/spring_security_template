package com.example.security.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.global.security.filter.JwtAuthenticationFilter;
import com.example.security.global.security.filter.UsernamePasswordFilter;
import com.example.security.global.security.handler.LoginCommonFailureHandler;
import com.example.security.global.security.handler.LoginCommonSuccessHandler;
import com.example.security.global.security.provider.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig {
	private final LoginCommonFailureHandler loginCommonFailureHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {
		setBasicSettings(http);
		setUrlSettings(http);
		addFilter(http);
		return http.build();
	}

	private void setBasicSettings(HttpSecurity http) throws Exception {
		http
			.csrf(CsrfConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.headers(
				headersConfigurer ->
					headersConfigurer
						.frameOptions(
							HeadersConfigurer.FrameOptionsConfig::sameOrigin
						)
			);
	}

	private void setUrlSettings(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequest ->
						authorizeRequest
								.requestMatchers(
										AntPathRequestMatcher.antMatcher("/auth/**")
								).permitAll()
								.requestMatchers(
										AntPathRequestMatcher.antMatcher("/h2-console/**")
								).permitAll()
								.anyRequest().authenticated()
				);
	}

	private void addFilter(HttpSecurity http) throws Exception {
		UsernamePasswordFilter usernamePasswordFilter = usernamePasswordFilter(http);
		http.addFilterAt(usernamePasswordFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public UsernamePasswordFilter usernamePasswordFilter(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
		AuthenticationManager authenticationManager = sharedObject.build();

		http.authenticationManager(authenticationManager);
		UsernamePasswordFilter usernamePasswordFilter = new UsernamePasswordFilter("/auth/login",
			authenticationManager, tokenProvider());
		usernamePasswordFilter.setAuthenticationFailureHandler(loginCommonFailureHandler);
		usernamePasswordFilter.setAuthenticationSuccessHandler(loginCommonSuccessHandler());
		return usernamePasswordFilter;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(tokenProvider());
	}

	@Bean
	public TokenProvider tokenProvider() {
		return new TokenProvider();
	}


	@Bean
	public LoginCommonSuccessHandler loginCommonSuccessHandler() {
		return new LoginCommonSuccessHandler(tokenProvider());
	}

}
