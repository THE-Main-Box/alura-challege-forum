package br.com.alura.Forum_Hub.domain.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicRegisterDataDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String userLogin,
        @NotBlank
        String course
) {
}
