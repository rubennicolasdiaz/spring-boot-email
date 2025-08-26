package com.rubennicolas.email_server_spring_boot.IntegrationTest;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import javax.mail.internet.MimeMessage;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmailServiceImplIT {

    @Autowired
    private IEmailService emailService;

    private GreenMail greenMail;

    @TestConfiguration
    static class GreenMailConfig {
        @Bean
        public JavaMailSender javaMailSender() {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost("localhost");
            sender.setPort(ServerSetupTest.SMTP.getPort());
            return sender;
        }
    }

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void sendEmail_ShouldSendMessageToGreenMail() throws Exception {
        EmailDTO dto = new EmailDTO("Juan", "juan@test.com", "Asunto", "Mensaje de prueba");

        // Se envía al GreenMail fake
        emailService.sendEmail(dto);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        assertThat(receivedMessages[0].getSubject()).isEqualTo("Asunto");
    }
}