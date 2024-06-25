package br.com.alura.Forum_Hub.infra.service.like.validation.interfaces;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;

public interface ValidateTopicLikeing {
    void validate(LikeRegisterDataDTO dataDTO);
}
