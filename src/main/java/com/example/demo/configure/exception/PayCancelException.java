package com.example.demo.configure.exception;

import com.example.demo.common.StatusCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayCancelException extends RuntimeException{
    StatusCodeEnum statusCodeEnum;

    String errorMessage;

    public PayCancelException(StatusCodeEnum statusCodeEnum) {
        this.statusCodeEnum = statusCodeEnum;
    }

    public PayCancelException(StatusCodeEnum statusCodeEnum, String errorMessage){
        this.statusCodeEnum = statusCodeEnum;
        this.errorMessage = errorMessage;
    }
}
