package com.login.web;

import com.login.service.UserService;
import com.login.web.dto.request.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signUp")
    public void signUp(@RequestBody UserInfoDTO userInfoDTO) {
        logger.info("회원가입 요청");
        userService.save(userInfoDTO);
    }

    @PostMapping("/login")
    public UserInfoDTO login(@RequestBody UserInfoDTO userInfoDTO) {
        logger.info("로그인 요청");
        return userService.login(userInfoDTO);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String auth) {
        logger.info("로그아웃 요청");
        userService.logout(auth);
    }

    @GetMapping("/myInfo")
    public UserInfoDTO viewMyInfo(@RequestHeader("Authorization") String auth) {
        logger.info("내 정보 조회");
        return userService.viewMyInfo(auth);
    }
}
