package br.com.alura.Forum_Hub.domain.model.like;

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

    @Enumerated(EnumType.ORDINAL)
    private Likables likedItem;

    @Column(name = "item_curtido_id")
    private Long likedItemId;

    @ManyToOne
    private User user;

    public Like(Likables likedItem, User personThatLiked) {
        this.likedItem= likedItem;
        this.user = personThatLiked;
    }
}
