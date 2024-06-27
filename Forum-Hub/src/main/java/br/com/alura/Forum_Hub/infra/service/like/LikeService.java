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

import java.util.List;
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

        if (like.isEmpty()) {
            throw new EntityIntegrityDataException("topico já foi discurtido");
        }

        var user = userRepository.findById(like.get().getUser().getId());
        List<Like> updatedLikeList = user.get().getLikeList();
        Optional<Like> likeToDelete = updatedLikeList.stream().filter(ll -> ll.getId().equals(like.get().getId())).findFirst();
        if (likeToDelete.isPresent()) {
            updatedLikeList.remove(likeToDelete.get());
            user.get().setLikeList(updatedLikeList);
            likeRepository.deleteById(likeToDelete.get().getId());
            userRepository.save(user.get());
        }

    }
}
