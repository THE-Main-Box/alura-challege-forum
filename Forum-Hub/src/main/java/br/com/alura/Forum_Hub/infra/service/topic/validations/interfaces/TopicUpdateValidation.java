package br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicUpdateDataDTO;

public interface TopicUpdateValidation {
    void validate(TopicUpdateDataDTO dataDTO);
}
