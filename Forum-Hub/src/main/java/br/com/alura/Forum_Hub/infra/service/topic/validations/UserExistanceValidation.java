package br.com.alura.Forum_Hub.infra.service.topic.validations;

import br.com.alura.Forum_Hub.domain.dto.topic.TopicRegisterDataDTO;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.exception.EntityIntegrityDataException;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import br.com.alura.Forum_Hub.infra.service.topic.validations.interfaces.TopicCrationValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserExistanceValidation implements TopicCrationValidation {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(TopicRegisterDataDTO dataDTO) {
        Optional<User> user = userRepository.findUserByLogin(dataDTO.userLogin());
        if (dataDTO.userLogin().isBlank()) {
            throw new EntityIntegrityDataException("para criar um tópico é nescessario passar um usuario");
        } else if(user.isEmpty()){
            throw new EntityNotFoundException("para criar um tópico é nescessario um usuario existente");
        }
    }
}
