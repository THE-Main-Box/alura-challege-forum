package br.com.alura.Forum_Hub.domain.dto.post;

import br.com.alura.Forum_Hub.domain.model.post.Post;

import java.time.LocalDateTime;

public record PostTopicDataDTO(
        Long id,
        String userName,
        String message,
        LocalDateTime creationDateTime,
        Boolean likedFromTopicOwner
) {
    public PostTopicDataDTO (Post p){
        this(
                p.getId(),
                p.getUser().getLogin(),
                p.getUserResponse(),
                p.getCreationDateTime(),
                p.isLikedFromTopicAuthor()
        );
    }
}
