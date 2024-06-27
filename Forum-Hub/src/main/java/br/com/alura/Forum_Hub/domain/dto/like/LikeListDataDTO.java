package br.com.alura.Forum_Hub.domain.dto.like;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import jakarta.validation.constraints.NotNull;

public record LikeListDataDTO(
        @NotNull
        Likables entityType,
        @NotNull
        Long entityId
) {
}
