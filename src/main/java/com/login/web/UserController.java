package com.login.web;

import com.login.service.UserService;
import com.login.web.dto.request.UserInfoDTO;
import com.login.web.dto.request.UserInfoUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signUp")
    @ResponseStatus(code = HttpStatus.CREATED)
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

    @GetMapping("/userInfo")
    public UserInfoDTO viewUserInfo(@RequestHeader("Authorization") String auth) {
        logger.info("유저 정보 조회 요청");
        return userService.viewUserInfo(auth);
    }

    @PatchMapping("/userInfo")
    public void updateUserInfo(@RequestHeader("Authorization") String auth, @RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        logger.info("유저 정보 수정 요청");
        userService.updateUserInfo(auth, userInfoUpdateDTO);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestHeader("Authorization") String auth, @RequestBody UserInfoDTO userInfoDTO) {
        logger.info("회원 탈퇴 요청");
        userService.deleteUser(auth, userInfoDTO);
    }
}
