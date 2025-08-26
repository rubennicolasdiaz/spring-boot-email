package com.rubennicolas.email_server_spring_boot.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubennicolas.email_server_spring_boot.services.IEmailService;
import com.rubennicolas.email_server_spring_boot.services.models.EmailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmailControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IEmailService emailService;

    @Test
    void sendEmailEndpoint_ShouldCallServiceAndReturnOk() throws Exception {
        EmailDTO dto = new EmailDTO("Juan", "juan@test.com", "Asunto", "Mensaje de prueba");

        mockMvc.perform(post("/send-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email enviado satisfactoriamente"));

        verify(emailService).sendEmail(dto);
    }
}