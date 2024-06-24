package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest

@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TopicControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;


    private final String TOPIC_REGISTER_URL;

    public TopicControllerTest(){
        this.TOPIC_REGISTER_URL = "/topic/register";
    }

    @Test
    @DisplayName("deveria retornar http:201 quando criado corretamente")
    @WithMockUser
    public void registerTopic_Scene1() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setValidData();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());
    }

    @Test
    @DisplayName("deveria retornar http:404 se não existir um usuario no banco")
    @WithMockUser
    public void registerTopic_Scene2() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setInvalidData();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isNotFound() )
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());
    }


    private TopicRegisterDataDTO setInvalidData() {
        User user = new User("usuario@email.com", "123456");
        user = userRepository.save(user);

        return new TopicRegisterDataDTO(
                "titulo",
                "descricao",
                "segundo_usuario@email.com",
                "teste"
        );
    }
    private TopicRegisterDataDTO setValidData() {
        User user = new User("usuario@email.com", "123456");
        user = userRepository.save(user);

        return new TopicRegisterDataDTO(
                "titulo",
                "descricao",
                user.getLogin(),
                "teste"
                );
    }

}