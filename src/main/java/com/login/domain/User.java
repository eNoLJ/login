package com.login.domain;

import com.login.web.dto.request.UserInfoDTO;
import com.login.web.dto.request.UserInfoUpdateDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
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

    public void saveToken(String token) {
        this.token = token;
    }

    public void removeToken() {
        token = null;
    }

    public void update(UserInfoUpdateDTO userInfoUpdateDTO) {
        this.password = userInfoUpdateDTO.getNewPassword();
        this.name = userInfoUpdateDTO.getName();
    }
}
