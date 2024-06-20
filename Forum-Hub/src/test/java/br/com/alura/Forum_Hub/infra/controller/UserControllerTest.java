package br.com.alura.Forum_Hub.infra.controller;

import br.com.alura.Forum_Hub.domain.dto.user.AuthenticationDataDTO;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    private final String USER_REGISTER_URL, USER_LOGIN_URL;

    public UserControllerTest() {
        this.USER_REGISTER_URL = "/user/register";
        this.USER_LOGIN_URL = "/user/login";
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

        assertThat(token).isNotBlank();
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
    @DisplayName("deveria retornar http:200 qunado o login é passado corretamente e é retornado um token")
    public void loginUser_Scene1() throws Exception {

        User savedUser = userRepository.save(new User("usuario", passwordEncoder.encode("123456")));

        var response = mvc.perform(post(USER_LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthenticationDataDTO(savedUser.getLogin(),"123456"))))
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());
        assertThat(response.getContentAsString()).isNotBlank();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}