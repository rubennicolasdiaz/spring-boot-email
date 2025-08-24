package com.rubennicolas.email_server_spring_boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
public class HealthController {

    @GetMapping("/health")
    public String checkServiceHealth() {
        log.info("Checking service health every 5 minutes from GitHub Actions");

        return "OK";
    }
}