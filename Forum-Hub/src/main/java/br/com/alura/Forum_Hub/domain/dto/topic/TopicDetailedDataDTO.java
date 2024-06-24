package br.com.alura.Forum_Hub.domain.dto.topic;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TopicDetailedDataDTO(
        Long id,
        String title,
        LocalDateTime creationDateTime,
        Long userID,
        String course,
        Boolean deleted,
        List<Long> post_id_list,
        Likables entityType
) {
    public TopicDetailedDataDTO(Topic t){
        this(
                t.getId(),
                t.getTitle(),
                t.getCreationDateTime(),
                t.getUser().getId(),
                t.getCourse(),
                t.isDeleted(),
                t.getPostList().stream().map(Post::getId).toList(),
                t.getEntityType()
        );
    }
}
