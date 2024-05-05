package com.example.security.domain.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.global.common.response.BaseResponse;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("")
	public BaseResponse testAdmin() {
		return BaseResponse.ok();
	}
}
