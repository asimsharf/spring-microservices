package com.sudagoarth.api_gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/user-service")
    public String userServiceFallback() {
        return "User Service is currently unavailable. Please try again later.";
    }
}
