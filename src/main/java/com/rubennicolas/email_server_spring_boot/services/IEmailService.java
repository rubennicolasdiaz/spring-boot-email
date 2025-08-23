package com.rubennicolas.email_server_spring_boot.services;

import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;

public interface IEmailService {

    void sendEmail(EmailDTO emailDTO);
}
