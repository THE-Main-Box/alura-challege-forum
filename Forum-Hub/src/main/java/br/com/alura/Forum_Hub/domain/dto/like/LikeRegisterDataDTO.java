package br.com.alura.Forum_Hub.domain.dto.like;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LikeRegisterDataDTO(@NotBlank String userLogin, @NotNull Likables likedItem, @NotNull Long likedItemId) {
}
