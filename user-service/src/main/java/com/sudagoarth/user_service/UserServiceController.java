package com.sudagoarth.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

    @GetMapping
    public String home() {
        return "🚀 User Service Gateway is running!";
    }
}
