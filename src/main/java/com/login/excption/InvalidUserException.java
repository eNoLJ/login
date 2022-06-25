package com.login.excption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("권한이 없는 유저입니다.");
    }
}
