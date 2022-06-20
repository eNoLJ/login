package com.login.excption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DoubleCheckException extends RuntimeException {

    public DoubleCheckException() {
        super("중복 된 이메일 입니다.");
    }
}
