package com.rubennicolas.email_server_spring_boot.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class HealthController {

    @GetMapping("/health")
    public String checkServiceHealth() {
        return "OK";
    }
}