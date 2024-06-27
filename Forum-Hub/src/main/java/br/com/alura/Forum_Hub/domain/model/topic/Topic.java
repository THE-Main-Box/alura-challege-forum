package br.com.alura.Forum_Hub.domain.model.topic;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String message;
    private LocalDateTime creationDateTime;
    private boolean answered;
    @ManyToOne
    private User user;
    private String course;
    private boolean deleted;

    @OneToMany(/*fetch = FetchType.EAGER,*/ cascade = CascadeType.ALL, mappedBy = "topicOfPost")
    List<Post> postList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Likables entityType = Likables.TOPIC;

    public Topic(String title, String message, String course, User user) {
        this.title = title;
        this.message = message;
        this.course = course;
        this.user = user;
        this.deleted = false;
        this.answered = false;
        this.creationDateTime = LocalDateTime.now();
    }

    public void responseIsValid() {
        this.answered = true;
    }

    public void deleteTopic() {
        this.deleted = true;
    }
}
