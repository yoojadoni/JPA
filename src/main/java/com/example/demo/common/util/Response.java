package com.example.demo.common.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> extends basicResponse {

    private T data;

    @Builder.Default
    private String message = "";
    //default OK
//    private HttpStatus status = HttpStatus.OK;
    @Builder.Default
    private int status = 200;
   /* public Response(T data) {
        this.data = data;
        if(data instanceof List) {
            this.count = ((List<?>)data).size();
        } else {
            this.count = 1;
        }
    }*/
}
