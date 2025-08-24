package com.rubennicolas.email_server_spring_boot.services.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveService {

    private static final String RENDER_URL = "https://spring-boot-email.onrender.com/actuator/health";
    private final RestTemplate restTemplate = new RestTemplate();

    // Ejecuta cada 5 minutos para que el servicio desplegado en Render no entre en hibernación
    @Scheduled(fixedRate = 300000)
    public void pingApp() {
        try {
            String response = restTemplate.getForObject(RENDER_URL, String.class);
            System.out.println("KeepAlive ping OK: " + response);
        } catch (Exception e) {
            System.err.println("KeepAlive ping FAILED: " + e.getMessage());
        }
    }
}
