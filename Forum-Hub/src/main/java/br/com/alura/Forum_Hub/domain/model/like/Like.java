package br.com.alura.Forum_Hub.domain.model.like;

import br.com.alura.Forum_Hub.domain.model.post.Post;
import br.com.alura.Forum_Hub.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curtidas")
@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Post post;

    @ManyToOne
    private User user;

    public Like(Post post, User personThatLiked) {
        this.post = post;
        this.user = personThatLiked;
    }
}
