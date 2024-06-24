package br.com.alura.Forum_Hub.domain.dto.topic;

import br.com.alura.Forum_Hub.domain.model.topic.Topic;

import java.time.LocalDateTime;

public record TopicListDataDTO(
        Long id,
        String title,
        String message,
        LocalDateTime creationDateTime,
        String course
) {
    public TopicListDataDTO(Topic t) {
        this(t.getId(), t.getTitle(), t.getMessage(), t.getCreationDateTime(), t.getCourse());
    }
}
