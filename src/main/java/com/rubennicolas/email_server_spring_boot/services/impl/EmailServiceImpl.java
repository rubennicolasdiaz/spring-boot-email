package com.rubennicolas.email_server_spring_boot.services.impl;

import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${email.username}")
    private String email;

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(email);
            helper.setTo(email);

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