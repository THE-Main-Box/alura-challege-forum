package br.com.alura.Forum_Hub.domain.model.topic;

import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private LocalDateTime creationDateTime;
    private boolean responded;
    @ManyToOne
    private User user;
    private String course;
    private boolean deleted;

    @OneToMany(/*fetch = FetchType.EAGER,*/ cascade =  CascadeType.ALL,
    mappedBy = "topicOfPost")
    List<Post> postList = new ArrayList<>();

    public Topic(String title, String message, String course, User user) {
        this.title = title;
        this.message = message;
        this.course = course;
        this.user = user;
        this.deleted = false;
        this.responded = false;
        this.creationDateTime = LocalDateTime.now();
    }

    public void responseIsValid(){
        this.responded = true;
    }

    public void deleteTopic(){
        this.deleted =true;
    }
}
