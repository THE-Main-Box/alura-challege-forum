package br.com.alura.Forum_Hub.domain.model.post;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import br.com.alura.Forum_Hub.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private User user;
    private String userResponse;
    private LocalDateTime creationDateTime;
    private boolean deleted;
    private boolean likedFromTopicAuthor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Topic topicOfPost;

    @Enumerated(EnumType.ORDINAL)
    private Likables entityType = Likables.POST;

    public Post(User user, String userResponse, Topic topic) {
        this.user = user;
        this.userResponse = userResponse;
        this.creationDateTime = LocalDateTime.now();
        this.topicOfPost = topic;
        this.deleted = false;
        this.likedFromTopicAuthor = false;
    }
}
