package br.com.alura.Forum_Hub.domain.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicUpdateDataDTO(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String course
) {
}
