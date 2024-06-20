package br.com.alura.Forum_Hub.domain.dto.user;

import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.domain.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDetailedDataDTO(
        Long id,
        String login,
        String password,
        List<Long> topicId,
        List<Long> postId,
        List<Long> likeId
) {

    public UserDetailedDataDTO(User user){
        this(user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getTopicList().stream().map(Topic::getId).toList(),
                user.getPostList().stream().map(Post::getId).toList(),
                user.getLikeList().stream().map(Like::getId).toList()
        );
    }
}
