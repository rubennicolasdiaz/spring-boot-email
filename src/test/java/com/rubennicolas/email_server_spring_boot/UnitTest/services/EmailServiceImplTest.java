package com.rubennicolas.email_server_spring_boot.UnitTest.services;

import com.rubennicolas.email_server_spring_boot.services.impl.EmailServiceImpl;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUpEmail() {
        ReflectionTestUtils.setField(emailService, "email", "${username}");
    }

    @Test
    void sendEmail_ShouldCallMailSenderAndTemplateEngine() {
        // given
        EmailDTO dto = new EmailDTO("Juan", "juan@test.com", "Prueba", "Mensaje de prueba");
        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("principal"), any(Context.class))).thenReturn("<html>Contenido</html>");

        // when
        emailService.sendEmail(dto);

        // then
        verify(templateEngine, times(1)).process(eq("principal"), any(Context.class));
        verify(mailSender, times(1)).send(mimeMessage);
    }
}
