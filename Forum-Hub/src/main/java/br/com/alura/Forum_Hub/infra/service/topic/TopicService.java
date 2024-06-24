package br.com.alura.Forum_Hub.infra.service.topic;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.topic.validations.TopicCrationValidation;
import br.com.alura.Forum_Hub.infra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private List<TopicCrationValidation> topicCreationValidation;

    public Topic getTopicById(Long id) {
        return topicRepository.getReferenceById(id);
    }

    public Topic createTopicObject(TopicRegisterDataDTO dataDTO) {
        this.topicCreationValidation.forEach(tcv -> tcv.validate(dataDTO));
        return topicRepository.save(
                new Topic(
                        dataDTO.title(),
                        dataDTO.message(),
                        dataDTO.course(),
                        userService.getUserByLogin(dataDTO.userLogin())
                )
        );
    }
}
