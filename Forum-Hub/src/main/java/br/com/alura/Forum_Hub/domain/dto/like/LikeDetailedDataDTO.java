package br.com.alura.Forum_Hub.domain.dto.like;

import br.com.alura.Forum_Hub.domain.dto.user.UserDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;

public record LikeDetailedDataDTO(
        Long id,
        Likables likedItem,
        Long likedItemId,
        String userName,
        Long userId
) {
    public LikeDetailedDataDTO(Like l){
        this(
                l.getId(),
                l.getLikedItem(),
                l.getLikedItemId(),
                l.getUser().getUsername(),
                l.getUser().getId()
        );
    }
}
