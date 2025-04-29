package com.sudagoarth.config_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigServer {
    @GetMapping
    public String home() {
        return "Config Server is running!";
    }
}
