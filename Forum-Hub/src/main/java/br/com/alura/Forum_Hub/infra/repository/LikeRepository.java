package br.com.alura.Forum_Hub.infra.repository;

import br.com.alura.Forum_Hub.domain.model.like.Likables;
import br.com.alura.Forum_Hub.domain.model.like.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.user.login = :userLogin")
    Optional<Like> findByUserLogin(String userLogin);
    @Query("SELECT l FROM Like l WHERE l.likedItem = :entity")
    Optional<Like> findByLikableEntity(Likables entity);

    @Query("SELECT l FROM Like l WHERE " +
            "l.user.login = :userLogin " +
            "AND l.likedItem = :entity " +
            "AND l.likedItemId = :likedItemId")
    Optional<Like> findByUserLoginAndEntityTypeAndById(String userLogin, Likables entity, Long likedItemId);

    @Query("SELECT l FROM Like l WHERE " +
            "l.likedItem = :likableEntityType " +
            "AND l.likedItemId = :entityId")
    Page<Like> findByLikableEntityAndLikedItemId(Likables likableEntityType, Long entityId, Pageable page);

}
