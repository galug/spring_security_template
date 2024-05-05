package com.example.security.global.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.domain.auth.entity.User;
import com.example.security.domain.auth.service.UserService;
import com.example.security.global.security.details.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	private final BCryptPasswordEncoder encoder;
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		log.info("CustomUserDetailsService, ==================");
		log.info(userId);
		User user = userService.findByUserId(userId);
		log.info(user.toString());
		return new CustomUserDetails(user);
	}
}
