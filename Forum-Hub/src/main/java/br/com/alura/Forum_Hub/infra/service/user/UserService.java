package br.com.alura.Forum_Hub.infra.service.user;

import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByLogin(String userLogin){
        return (User) userRepository.findByLogin(userLogin);
    }
}
