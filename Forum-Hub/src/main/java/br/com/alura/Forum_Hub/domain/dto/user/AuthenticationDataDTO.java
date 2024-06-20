package br.com.alura.Forum_Hub.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDataDTO (@NotBlank String login, @NotBlank String password){

}
