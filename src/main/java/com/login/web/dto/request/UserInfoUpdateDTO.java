package com.login.web.dto.request;

import lombok.Getter;

@Getter
public class UserInfoUpdateDTO {

    private String email;
    private String currentPassword;
    private String newPassword;
    private String name;
}
