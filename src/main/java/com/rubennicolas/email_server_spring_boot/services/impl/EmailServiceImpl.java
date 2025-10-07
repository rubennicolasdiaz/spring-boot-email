package com.rubennicolas.email_server_spring_boot.services.impl;

import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${username}")
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

            String contentHtml = templateEngine.process("principal", context);
            helper.setText(contentHtml, true);

            log.debug("Intentando enviar correo a {} con asunto '{}'", email, emailDTO.getSubject());
            javaMailSender.send(mimeMessage);

            log.info("Correo enviado exitosamente a {}", email);

        } catch (MailSendException e) {
            log.error("Error de conexión con el servidor SMTP: {}", e.getMessage(), e);
            throw new RuntimeException("Error al conectar con el servidor SMTP", e);

        } catch (MessagingException e) {
            log.error("Error de formato o construcción del mensaje: {}", e.getMessage(), e);
            throw new RuntimeException("Error al construir el correo", e);

        } catch (Exception e) {
            log.error("Error inesperado al enviar correo: {}", e.getMessage(), e);
            throw new RuntimeException("Error general al enviar correo", e);
        }
    }
}