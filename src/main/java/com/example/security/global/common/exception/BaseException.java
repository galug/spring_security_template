package com.example.security.global.common.exception;


import com.example.security.global.common.response.BaseResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
	private BaseResponseStatus status;
}
