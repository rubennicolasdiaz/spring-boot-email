package com.rubennicolas.email_server_spring_boot.UnitTest.controllers;

import com.rubennicolas.email_server_spring_boot.controllers.EmailController;
import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmailControllerTest {

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @Test
    void sendEmail_ShouldReturnOkResponse() {
        // given
        EmailDTO dto = new EmailDTO("name", "name@test.com", "asunto", "mensaje");

        // when
        ResponseEntity<String> response = emailController.sendEmail(dto);

        // then
        verify(emailService, times(1)).sendEmail(dto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Email enviado satisfactoriamente", response.getBody());
    }
}