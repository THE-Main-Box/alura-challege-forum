package br.com.alura.Forum_Hub.infra.service.user;

import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByLogin(String userLogin){
        return (User) userRepository.findByLogin(userLogin);
    }

    @Transactional
    public void addLike(Like like) {
        Optional<User> user = userRepository.findById(like.getUser().getId());
        if(user.isPresent()){
            user.get().addLike(like);
        }
    }
}
