package com.example.demo.test;

import com.example.demo.configure.firebase.FCMInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @Autowired
    private FCMInitializer fcmInitializer;

    @GetMapping("/testfcm")
    public String test(){
        fcmInitializer.initialize();
        return "testfcm";
    }
}
