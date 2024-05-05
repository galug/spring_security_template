package com.example.security.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.domain.auth.dto.AuthDto;
import com.example.security.domain.auth.dto.UserNamePasswordDto;
import com.example.security.domain.auth.entity.Role;
import com.example.security.domain.auth.entity.Type;
import com.example.security.domain.auth.entity.User;
import com.example.security.domain.auth.repository.UserRepository;
import com.example.security.global.common.exception.BaseException;
import com.example.security.global.common.response.BaseResponseStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthDto findByUserId(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow();
		return new AuthDto(user);
	}

	public User findByUserId(String userId) {
		return userRepository.findUserByUserId(userId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_USER_EXCEPTION));
	}

	public void create(UserNamePasswordDto userNamePasswordDto) {
		User user = User.builder()
			.userId(userNamePasswordDto.getUserId())
			.password(passwordEncoder.encode(userNamePasswordDto.getPassword()))
			.type(Type.COMMON)
			.role(Role.USER)
			.build();
		userRepository.save(user);
	}
}
