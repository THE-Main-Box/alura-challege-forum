package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.user.AuthenticationDataDTO;
import br.com.alura.Forum_Hub.domain.dto.user.TokenJWTDataDTO;
import br.com.alura.Forum_Hub.domain.dto.user.UserDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private final String USER_REGISTER_URL, USER_LOGIN_URL, USER_LIST_URL;

    public UserControllerTest() {
        this.USER_REGISTER_URL = "/user/register";
        this.USER_LOGIN_URL = "/user/login";
        this.USER_LIST_URL = "/user/list";
    }

    @Test
    @DisplayName("deveria devolver http:201 quando criado com sucesso e o token for devolvido")
    public void registerUser_Scene1() throws Exception {
        var response = mvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthenticationDataDTO("usuario1", "123456"))))
                .andReturn().getResponse();

        String token = response.getContentAsString();
        System.out.println(token);

        String responseBody = response.getContentAsString();

        assertThat(responseBody).isNotBlank();

        // Verificar se o token retornado é um JWT válido
        TokenJWTDataDTO tokenData = objectMapper.readValue(responseBody, TokenJWTDataDTO.class);
        assertThat(tokenData.tokenJWT()).isNotBlank();

        // Opcional: validar se o token é válido usando o serviço de token
        tokenService.validateToken(tokenData.tokenJWT());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("deveria devolver http:400 quando algum dado não estiver sendo passado ou declarado como blank pela jpa")
    public void registerUser_Scene2() throws Exception {
        var response = mvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthenticationDataDTO("", null))))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("deveria retornar http:200 quando o login é passado corretamente e é retornado um token")
    public void loginUser_Scene1() throws Exception {

        AuthenticationDataDTO userToSave = new AuthenticationDataDTO("usuario", "123456");

        // Registrar usuário
        var registerResponse = mvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToSave)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        User savedUser = (User) userRepository.findByLogin(userToSave.login());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo(userToSave.login());

        // Fazer login
        var response = mvc.perform(post(USER_LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToSave)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        System.out.println(responseBody);

        assertThat(responseBody).isNotBlank();

        // Verificar se o token retornado é um JWT válido
        TokenJWTDataDTO tokenData = objectMapper.readValue(responseBody, TokenJWTDataDTO.class);
        assertThat(tokenData.tokenJWT()).isNotBlank();

        // Opcional: validar se o token é válido usando o serviço de token
        tokenService.validateToken(tokenData.tokenJWT());
    }

    @Test
    @DisplayName("Deveria devolver http:200 quando devolver corretamente a autorização")
    @WithMockUser
    public void listUser_Scene1() throws Exception {
        AuthenticationDataDTO userToSave = new AuthenticationDataDTO("teste_2@email.com", "123456");

        // Registrar usuário
        var registerResponse = mvc.perform(post(USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToSave)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        User savedUser = (User) userRepository.findByLogin(userToSave.login());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo(userToSave.login());

        var listResponse = mvc.perform(get(USER_LIST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

}