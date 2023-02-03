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

    PRICE_NOT_MATCH(410, "금액이 잘못되었습니다"),
    DELIVERY_PRICE_NOT_MATCH(411, "배달금액이 정확하지 않습니다"),
    MENU_NOT_MATCH(412, "가게에서 판매하지 않는 메뉴가 존재합니다."),
    COUPON_ISSUED(420, "쿠폰이 이미사용되었습니다"),

    PAYMENT_NOT_MATCH_FROM_PG(430, "결제금액이 PG사와 동일하지 않습니다."),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
    DB_ERROR(600, "DB_ERROR");

    @Getter
    private final int statusCode;
    @Setter
    @Getter
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
