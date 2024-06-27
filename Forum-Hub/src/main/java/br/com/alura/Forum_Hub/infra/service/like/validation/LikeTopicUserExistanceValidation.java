package br.com.alura.Forum_Hub.infra.service.like.validation;

import br.com.alura.Forum_Hub.domain.dto.like.LikeRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.like.validation.interfaces.ValidateTopicLikeing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LikeTopicUserExistanceValidation implements ValidateTopicLikeing {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(LikeRegisterDataDTO dataDTO) {
        Optional<User> userToFind = userRepository.findUserByLogin(dataDTO.userLogin());

        if (userToFind.isEmpty()) {
            throw  new EntityIntegrityDataException("para curtir um topico voce precisa estar logado");
        }
    }
}
