package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;

public enum StatusCodeEnum {
    OK(200, "OK"),
    CREATED(201, "CREATED_OK"),
    UPDATE_SUCCESS(202, "UPDATE_OK"),
    DELETE_SUCCESS(203, "DELETE_OK"),
    NO_CONTENT(204, "NO_CONTENT"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    NO_DATA(405, "DATA_NOT_EXISTS"),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
    DB_ERROR(600, "DB_ERROR");

    @Getter
    private final int statusCode;
    @Setter
    private final String codeName;

    StatusCodeEnum(int statusCode, String codeName) {
        this.statusCode = statusCode;
        this.codeName = codeName;
    }

    public int getCode(){
        return this.statusCode;
    }

    public String getName(){
        return this.codeName;
    }

    public static StatusCodeEnum getByValue(int statusCode) {
        for(StatusCodeEnum status : values()) {
            if(status.statusCode == statusCode) return status;
        }
        throw new IllegalArgumentException("Invalid status code: " + statusCode);
    }

}
