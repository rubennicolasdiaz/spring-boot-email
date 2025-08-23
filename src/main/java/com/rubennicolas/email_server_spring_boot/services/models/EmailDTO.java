package com.rubennicolas.email_server_spring_boot.services.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {

    private String name;
    private String email;
    private String subject;
    private String message;
}
