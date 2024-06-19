package br.com.alura.Forum_Hub.infra.repository;

import br.com.alura.Forum_Hub.domain.model.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
