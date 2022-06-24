package com.login.web.dto.request;

import com.login.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String token;

    public static UserInfoDTO createLoginInfo(User user) {
        return UserInfoDTO.builder()
                .token(user.getToken())
                .build();
    }

    public static UserInfoDTO createUserInfo(User user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
