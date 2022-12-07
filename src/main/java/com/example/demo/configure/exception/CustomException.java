package com.example.demo.configure.exception;

import com.example.demo.common.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    StatusCodeEnum statusCodeEnum;

    String errorMessage;

    public CustomException(StatusCodeEnum statusCodeEnum) {
        this.statusCodeEnum = statusCodeEnum;
    }

    public CustomException(StatusCodeEnum statusCodeEnum, String errorMessage){
        this.statusCodeEnum = statusCodeEnum;
        this.errorMessage = errorMessage;
    }
}
