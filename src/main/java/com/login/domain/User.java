package com.login.domain;

import com.login.web.dto.request.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String token;

    public static User create(UserInfoDTO userInfoDTO) {
        return User.builder()
                .email(userInfoDTO.getEmail())
                .password(userInfoDTO.getPassword())
                .name(userInfoDTO.getName())
                .build();
    }
}
