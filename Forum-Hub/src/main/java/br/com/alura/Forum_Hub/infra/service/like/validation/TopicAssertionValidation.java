package br.com.alura.Forum_Hub.infra.service.like.validation;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.service.like.validation.interfaces.ValidateTopicLikeing;
import org.springframework.stereotype.Component;

@Component
public class TopicAssertionValidation implements ValidateTopicLikeing {
    @Override
    public void validate(LikeRegisterDataDTO dataDTO) {
        if(!dataDTO.likedItem().equals(Likables.TOPIC)){
            throw new EntityIntegrityDataException("Para curtir um topico é nescessario passar um entityType de TOPIC");
        }
    }
}
