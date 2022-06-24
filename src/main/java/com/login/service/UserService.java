package com.login.service;

import com.login.auth.service.JwtService;
import com.login.domain.User;
import com.login.domain.UserRepository;
import com.login.excption.DoubleCheckException;
import com.login.excption.UserNotFoundException;
import com.login.web.dto.request.UserInfoDTO;
import com.login.web.dto.request.UserInfoUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void save(UserInfoDTO userInfoDTO) {
        if (doubleCheck(userInfoDTO.getEmail())) {
            throw new DoubleCheckException();
        }
        userRepository.save(User.create(userInfoDTO));
    }

    public UserInfoDTO login(UserInfoDTO userInfoDTO) {
        User user = findByEmailAndPassword(userInfoDTO.getEmail(), userInfoDTO.getPassword());
        user.saveToken(jwtService.createToken(user));
        userRepository.save(user);
        return UserInfoDTO.createLoginInfo(user);
    }

    public void logout(String auth) {
        User user = findById(jwtService.getIdByAuth(auth));
        user.removeToken();
        userRepository.save(user);
    }

    public UserInfoDTO viewUserInfo(String auth) {
        User user = findById(jwtService.getIdByAuth(auth));
        return UserInfoDTO.createUserInfo(user);
    }

    public void updateUserInfo(String auth, UserInfoUpdateDTO userInfoUpdateDTO) {
        User user = findById(jwtService.getIdByAuth(auth));
        User targetUser = findByEmailAndPassword(userInfoUpdateDTO.getEmail(), userInfoUpdateDTO.getCurrentPassword());
        if (user.equals(targetUser)) {
            user.update(userInfoUpdateDTO);
            userRepository.save(user);
        }
    }

    private boolean doubleCheck(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(UserNotFoundException::new);
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
