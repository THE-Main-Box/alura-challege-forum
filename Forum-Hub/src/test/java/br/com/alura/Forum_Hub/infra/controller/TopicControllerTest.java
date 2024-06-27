package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicUpdateDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
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

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private TopicRepository topicRepository;


    private final String TOPIC_REGISTER_URL, TOPIC_UPDATE_URL, TOPIC_DELETE_URL, TOPIC_LIKE_URL, TOPIC_DISLIKE_URL;

    public TopicControllerTest() {
        this.TOPIC_REGISTER_URL = "/topic/register";
        this.TOPIC_UPDATE_URL = "/topic/update";
        this.TOPIC_DELETE_URL = "/topic/delete";
        this.TOPIC_LIKE_URL = "/like/topic";
        this.TOPIC_DISLIKE_URL = "/like/dislike/topic";
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
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());
    }


    @Test
    @DisplayName("deveria retornar http:200 quando atualizado corretamente")
    @WithMockUser
    public void updateTopic_Scene1() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setValidData();

        User user = userRepository.findUserByLogin(validDataDTO.userLogin()).get();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

        TopicUpdateDataDTO validUpdateDataDTO = this.setUpdateValidData(user.getId());

        var updateResponse = mvc.perform(put(TOPIC_UPDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateDataDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        System.out.println(updateResponse.getContentAsString());
    }

    @Test
    @DisplayName("deveria retornar http:200 quando deletado corretamente")
    @WithMockUser
    public void deleteTopic_Scene1() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setValidData();
        User user = userRepository.findUserByLogin(validDataDTO.userLogin()).get();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

        var updateResponse = mvc.perform(delete(TOPIC_DELETE_URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Topic> topicList = topicRepository.findAll();

        assertThat(topicList).isEmpty();
    }

    @Test
    @DisplayName("deveria retornar http:200 quando curtido corretamente")
    @WithMockUser
    public void likeTopic_Scene1() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setValidData();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

        Topic topic = topicRepository.findById(1L).get();
        LikeRegisterDataDTO validLikeData = new LikeRegisterDataDTO(
                topic.getUser().getLogin(),
                topic.getEntityType(),
                topic.getId()
        );

        var updateResponse = mvc.perform(put(TOPIC_LIKE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLikeData)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        System.out.println(userRepository.findById(1L).get().getLikeList().size());
    }


    @Test
    @DisplayName("deveria retornar http:200 quando descurtido corretamente")
    @WithMockUser
    public void disLikeTopic_Scene1() throws Exception {
        TopicRegisterDataDTO validDataDTO = this.setValidData();

        var response = mvc.perform(post(TOPIC_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDataDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

        User user = userRepository.findUserByLogin(validDataDTO.userLogin()).get();

        Topic topic = topicRepository.findByUserLogin(user.getLogin()).get();

        LikeRegisterDataDTO validLikeData = new LikeRegisterDataDTO(
                user.getLogin(),
                topic.getEntityType(),
                topic.getId()
        );

        var likeResponse = mvc.perform(put(TOPIC_LIKE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLikeData)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        System.out.println(userRepository.findById(user.getId()).get().getLikeList().size());

        LikeRegisterDataDTO validDislikeData = new LikeRegisterDataDTO(
                user.getLogin(),
                Likables.TOPIC,
                topic.getId()
        );

        var dislikeResponse = mvc.perform(delete(TOPIC_DISLIKE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDislikeData)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        user = userRepository.findUserByLogin(user.getLogin()).get();

        assertThat(user.getLikeList().size()).isEqualTo(0);

    }

    private TopicUpdateDataDTO setUpdateValidData(Long userId) {
        return new TopicUpdateDataDTO(
                userId,
                "titulo-2",
                "descricao",
                "teste-1"
        );
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