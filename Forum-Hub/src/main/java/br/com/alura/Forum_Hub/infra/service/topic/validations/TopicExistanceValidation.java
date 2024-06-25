package br.com.alura.Forum_Hub.infra.service.topic.validations;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicUpdateDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces.TopicUpdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopicExistanceValidation implements TopicUpdateValidation {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(TopicUpdateDataDTO dataDTO) {
        Optional<Topic> topic = topicRepository.findById(dataDTO.id());
        if(topic.isEmpty()){
            throw new EntityIntegrityDataException("topico informado não existente");
        }
    }
}
