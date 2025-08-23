package com.rubennicolas.email_server_spring_boot.services.impl;

import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String EMAIL_YAHOO = "ruben.nicolasdiaz@yahoo.com";

            helper.setFrom(EMAIL_YAHOO);
            helper.setTo(EMAIL_YAHOO);

            helper.setSubject(emailDTO.getSubject());

            // Variables para Thymeleaf
            Context context = new Context();
            context.setVariable("name", emailDTO.getName());
            context.setVariable("email", emailDTO.getEmail());
            context.setVariable("subject", emailDTO.getSubject());
            context.setVariable("message", emailDTO.getMessage());

            String contentHtml = templateEngine.process("email", context);

            helper.setText(emailDTO.getMessage(), contentHtml);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el email: " + e.getMessage());
        }
    }
}


