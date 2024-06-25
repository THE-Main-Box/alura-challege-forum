package br.com.alura.Forum_Hub.infra.service.like;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.LikeRepository;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.like.validation.interfaces.ValidateTopicLikeing;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<ValidateTopicLikeing> topicLikeValidation;

    @Transactional
    public Like likeTopic(LikeRegisterDataDTO dataDTO){
        topicLikeValidation.forEach(tlv -> tlv.validate(dataDTO));

        User user = userRepository.findUserByLogin(dataDTO.userLogin()).get();

        Like like = new Like(Likables.TOPIC, user, dataDTO.likedItemId());
        return likeRepository.save(like);
    }
}
