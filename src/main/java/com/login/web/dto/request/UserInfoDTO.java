package com.login.web.dto.request;

import lombok.Getter;

@Getter
public class UserInfoDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String token;
}
