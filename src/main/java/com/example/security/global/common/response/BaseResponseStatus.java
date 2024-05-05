package com.example.security.global.common.response;

import lombok.Getter;

@SuppressWarnings("checkstyle:NoWhitespaceBefore")
@Getter
public enum BaseResponseStatus {

	/**
	 * 200 : 요청 성공
	 */
	SUCCESS(200, "요청 성공"),

	/**
	 * 잘못된 요청 코드
	 */
	WRONG_METHOD_EXCEPTION(9009, "잘못된 메서드 요청입니다."),

	/**
	 * 토큰 만료 코드
	 */

	EXPIRATION_TOKEN(8008, "토큰 만료"),
	EMPTY_ACCESS_KEY(8008, "비어 있는 토큰"),
	NOT_VALID_TOKEN(8008, "유효하지 않은 토큰입니다."),

	INTERNET_SERVER_ERROR(5000, "인터넷 서버 에러입니다."),

	/**
	 * NOT FOUND ERROR
	 */
	NOT_USER_EXCEPTION(9009, "유저를 찾을 수 없습니다.");


	private final int code;
	private final String message;

	private BaseResponseStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
