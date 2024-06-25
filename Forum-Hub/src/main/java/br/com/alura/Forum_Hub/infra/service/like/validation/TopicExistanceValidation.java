package br.com.alura.Forum_Hub.infra.service.like.validation;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.like.validation.interfaces.ValidateTopicLikeing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopicExistanceValidation implements ValidateTopicLikeing {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(LikeRegisterDataDTO dataDTO) {
        Optional<Topic> topicToFind = topicRepository.findById(dataDTO.likedItemId());

        if (dataDTO.likedItemId().toString().isBlank()) {
            throw  new EntityIntegrityDataException("para curtir um topico voce precisa inserir um id de forma valida");
        } else if (topicToFind.isEmpty()) {
            throw new EntityIntegrityDataException("para curtir um topico é preciso que ele exista primeiro");
        }
    }
}
