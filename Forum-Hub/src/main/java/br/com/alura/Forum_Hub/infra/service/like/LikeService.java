package br.com.alura.Forum_Hub.infra.service.like;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.dto.topic.TopicDetailedDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.LikeRepository;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.topic.TopicService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicService topicService;


    @Transactional
    public void dislikeTopic(LikeRegisterDataDTO dataDTO) {

        Optional<Like> like = likeRepository.findByUserLoginAndEntityTypeAndById(
                dataDTO.userLogin(),
                dataDTO.likedItem(),
                dataDTO.likedItemId()
        );

        TopicDetailedDataDTO topic = topicService.getTopicById(dataDTO.likedItemId());

        if(like.isEmpty()){
            throw new EntityIntegrityDataException("topico já foi discurtido");
        }

        var user = userRepository.findById(like.get().getUser().getId());
        user.get().deleteLike(like.get().getId());
        likeRepository.deleteById(like.get().getId());
        userRepository.save(user.get());
    }
}
