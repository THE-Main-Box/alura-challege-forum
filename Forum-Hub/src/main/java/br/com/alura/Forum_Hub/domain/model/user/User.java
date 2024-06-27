package br.com.alura.Forum_Hub.domain.model.user;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Embeddable
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;
    @Column(unique = true)
    private String password;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Topic> topicList = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public void addPost(Post p) {
        this.postList.add(p);
    }

    public void addTopic(Topic t) {
        this.topicList.add(t);
    }

    public void addLike(Like l) {
        this.likeList.add(l);
    }


    public void deleteTopic(Long topicId) {
        this.topicList.remove(
                this.topicList.stream().filter(tl -> tl.getId().equals(topicId)).findFirst().get()
        );
    }

    public void deleteLike(Long likeId) {
        this.likeList.remove(
                this.likeList.stream().filter(ll -> ll.getId().equals(likeId)).findFirst().get()
        );
    }
}
