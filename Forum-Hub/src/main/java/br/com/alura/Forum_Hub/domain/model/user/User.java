package br.com.alura.Forum_Hub.domain.model.user;

import br.com.alura.Forum_Hub.domain.model.like.Like;
import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Embeddable
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    private List<Post> postList;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    private List<Like> likeList;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user")
    private List<Topic> topicList;

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
}
