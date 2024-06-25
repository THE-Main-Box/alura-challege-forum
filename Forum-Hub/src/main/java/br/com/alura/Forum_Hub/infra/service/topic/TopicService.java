package br.com.alura.Forum_Hub.infra.service.topic;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicListDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicUpdateDataDTO;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces.TopicCrationValidation;
import br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces.TopicUpdateValidation;
import br.com.alura.Forum_Hub.infra.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private List<TopicCrationValidation> topicCreationValidation;

    @Autowired
    private List<TopicUpdateValidation> topicUpdateValidation;

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

    public Topic updateTopic(TopicUpdateDataDTO dto) {
        topicUpdateValidation.forEach(tuv -> tuv.validate(dto));
        Topic t = topicRepository.findById(dto.id()).get();

        if(dto.title() != t.getTitle()){
            t.setTitle(dto.title());
        }
        if (dto.message() != t.getMessage()){
            t.setMessage(dto.message());
        }
        if (dto.course() != t.getCourse()){
            t.setCourse(dto.course());
        }

        return t;
    }

    @Transactional
    public void deleteTopic(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);

        if(topic.isEmpty()){
            throw new EntityNotFoundException("o id precisa ser de um topico existente");
        }else if(topic.get().isDeleted()){
            throw new EntityIntegrityDataException("não é possivel deletar um topico que já foi deletado");
        }
        topic.ifPresent(Topic::deleteTopic);
    }
}
