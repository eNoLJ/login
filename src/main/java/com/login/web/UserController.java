package com.login.web;

import com.login.service.UserService;
import com.login.web.dto.request.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signUp")
    public void signUp(@RequestBody UserInfoDTO userInfoDTO) {
        logger.debug("회원가입 요청");
        userService.save(userInfoDTO);
    }
}
