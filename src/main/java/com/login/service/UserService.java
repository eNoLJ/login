package com.login.service;

import com.login.domain.UserRepository;
import com.login.web.dto.request.SignUpInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
