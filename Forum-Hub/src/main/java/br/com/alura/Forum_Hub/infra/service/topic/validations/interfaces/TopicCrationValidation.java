package br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import org.springframework.stereotype.Component;

@Component
public interface TopicCrationValidation {
    void validate(TopicRegisterDataDTO dataDTO);
}
