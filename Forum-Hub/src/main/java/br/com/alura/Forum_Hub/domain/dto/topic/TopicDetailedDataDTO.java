package br.com.alura.Forum_Hub.domain.dto.topic;

import br.com.alura.Forum_Hub.domain.dto.post.PostTopicDataDTO;
import br.com.alura.Forum_Hub.domain.dto.user.UserDetailedDataDTO;
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
        String userName,
        String course,
        Boolean deleted,
        List<PostTopicDataDTO> post_id_list,
        Likables entityType
) {
    public TopicDetailedDataDTO(Topic t){
        this(
                t.getId(),
                t.getTitle(),
                t.getCreationDateTime(),
                t.getUser().getUsername(),
                t.getCourse(),
                t.isDeleted(),
                t.getPostList().stream().map(PostTopicDataDTO::new).toList(),
                t.getEntityType()
        );
    }
}
