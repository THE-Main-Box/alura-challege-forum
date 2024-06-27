package br.com.alura.Forum_Hub.infra.service.like.validation;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.LikeRepository;
import br.com.alura.Forum_Hub.infra.repository.TopicRepository;
import br.com.alura.Forum_Hub.infra.service.like.validation.interfaces.ValidateTopicLikeing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrevioslyLikedValidation implements ValidateTopicLikeing {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void validate(LikeRegisterDataDTO dataDTO) {

        Optional<Like> likedPreviosly = likeRepository.findByUserLoginAndEntityTypeAndById(
                dataDTO.userLogin(),
                dataDTO.likedItem(),
                dataDTO.likedItemId()
        );

        if(likedPreviosly.isPresent()){
            throw new EntityIntegrityDataException("não é possivel curitir a mesma publicação mais de 1 vez");
        }

    }
}
