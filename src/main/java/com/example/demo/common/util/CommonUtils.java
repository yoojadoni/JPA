package com.example.demo.common.util;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

    public CommonUtils(){}

    public static String getUserEmail(HttpServletRequest req){
        return (String) req.getAttribute("email");
    }
}
