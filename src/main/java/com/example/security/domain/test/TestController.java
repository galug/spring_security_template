package com.example.security.domain.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.global.common.response.BaseResponse;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("")
	public BaseResponse test() {
		return BaseResponse.ok();
	}
}
