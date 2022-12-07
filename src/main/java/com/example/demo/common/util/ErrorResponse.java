package com.example.demo.common.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    @Builder.Default
    private String errorMessage = "";
    @Builder.Default
    private int errorCode = 400;

}
