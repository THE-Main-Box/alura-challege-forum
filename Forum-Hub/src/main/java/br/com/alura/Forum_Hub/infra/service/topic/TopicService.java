package br.com.alura.Forum_Hub.infra.service.topic;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicListDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.topic.validations.TopicCrationValidation;
import br.com.alura.Forum_Hub.infra.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public TopicDetailedDataDTO getTopicById(Long id) {
        return new TopicDetailedDataDTO(topicRepository.getReferenceById(id));
    }

    @Transactional
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

    public Page<TopicListDataDTO> listTopicsPaged(Pageable pageable) {
        return topicRepository.findAllPagedDeletedFalse(pageable).map(TopicListDataDTO::new);
    }
}
