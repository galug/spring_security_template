package com.example.security.domain.auth.controller;

import com.example.security.domain.auth.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.domain.auth.dto.UserNamePasswordDto;
import com.example.security.domain.auth.service.UserService;
import com.example.security.global.common.response.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public void login() {
        log.info("로그인 완료~~~~~");
    }

    @PostMapping("/signup")
    public BaseResponse signup(@RequestBody UserNamePasswordDto userNamePasswordDto) {
        log.info("회원 가입 완료 ~~~~~~~`");
        userService.create(userNamePasswordDto);
        return BaseResponse.ok();
    }

    @PostMapping("/reissue")
    public BaseResponse reissue(@RequestBody AuthRequest.Reissue reissue) {
        log.info("회원 가입 완료 ~~~~~~~`");
        userService.reissue(reissue);
        return BaseResponse.ok();
    }

    @GetMapping("/test")
    public void test() {
        log.info("test");
    }
}
