package com.login.service;

import com.login.domain.User;
import com.login.domain.UserRepository;
import com.login.excption.DoubleCheckException;
import com.login.web.dto.request.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserInfoDTO userInfoDTO) {
        if (doubleCheck(userInfoDTO.getEmail())) {
            throw new DoubleCheckException();
        }
        userRepository.save(User.create(userInfoDTO));
    }

    private boolean doubleCheck(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
