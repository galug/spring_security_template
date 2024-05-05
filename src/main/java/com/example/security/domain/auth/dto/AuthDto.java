package com.example.security.domain.auth.dto;

import com.example.security.domain.auth.entity.User;
import com.example.security.domain.auth.entity.Role;

public class AuthDto {
	private Long userId;
	private Role role;

	public AuthDto(User user) {
		this.userId = user.getId();
		this.role = user.getRole();
	}

	public Long getUserId() {
		return userId;
	}

	public Role getUserRole() {
		return role;
	}
}
