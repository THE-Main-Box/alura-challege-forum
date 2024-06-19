package br.com.alura.Forum_Hub.infra.repository;

import br.com.alura.Forum_Hub.domain.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
